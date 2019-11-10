package com.moneytransfer.api;

import com.google.common.util.concurrent.Striped;
import com.moneytransfer.db.AccountDAO;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.locks.Lock;

@Singleton
public class TransactionService {
    private final AccountDAO accountDAO;

    private Map<String, Lock> lockMap;

    private Striped<Lock> locks;

    public TransactionService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        locks = Striped.lock(10);
    }

    public PostTransfer transfer(Transaction transaction){
        if (transaction.getDebitedAccount().equals(transaction.getCreditedAccount())) {
            throw new IllegalArgumentException("Transfers to same account is not allowed");
        }

        Lock firstToLock;
        Lock secondToLock;

        if (Integer.valueOf(transaction.getCreditedAccount()) < Integer.valueOf(transaction.getDebitedAccount())){
            firstToLock = locks.get(transaction.getCreditedAccount());
            secondToLock = locks.get(transaction.getDebitedAccount());
        } else {
            firstToLock = locks.get(transaction.getDebitedAccount());
            secondToLock = locks.get(transaction.getCreditedAccount());
        }

        try {
            firstToLock.lock();
            try {
                secondToLock.lock();
                Account debitedAccount = accountDAO.findByAccountNumber(transaction.getDebitedAccount());
                Account creditedAccount = accountDAO.findByAccountNumber(transaction.getCreditedAccount());

                debitedAccount.withdraw(transaction.getTransferAmount());
                creditedAccount.deposit(transaction.getTransferAmount());

                accountDAO.updateAccount(debitedAccount);
                accountDAO.updateAccount(creditedAccount);

                return new PostTransfer(debitedAccount, creditedAccount, transaction.getTransferAmount());
            }
            finally {
                secondToLock.unlock();
            }
        } finally {
            firstToLock.unlock();
        }
    }
}
