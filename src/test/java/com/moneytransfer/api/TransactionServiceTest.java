package com.moneytransfer.api;

import com.moneytransfer.db.AccountDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class TransactionServiceTest {
    private static final AccountDAO ACCOUNT_DAO = Mockito.mock(AccountDAO.class);
    private static final TransactionService TRANSACTION_SERVICE = new TransactionService(ACCOUNT_DAO);

    private Transaction transaction;
    private Account debitedAccount;
    private Account creditedAccount;

    @BeforeEach
    public void setUp() {
         transaction = new Transaction();
         transaction.setCreditedAccount("2");
         transaction.setDebitedAccount("1");
         transaction.setTransferAmount(BigDecimal.TEN);

        debitedAccount = Account.builder()
                .accountNumber("1")
                .balance(BigDecimal.valueOf(100))
                .build();

        creditedAccount = Account.builder()
                .accountNumber("2")
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    @Test
    public void testCannotWithdrawFromSameAccount(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transaction.setCreditedAccount("1");
            TRANSACTION_SERVICE.transfer(transaction);
        });
    }

    @Test
    public void testSuccessfulTransfer(){
        Mockito.when(ACCOUNT_DAO.findByAccountNumber(transaction.getDebitedAccount())).thenReturn(debitedAccount);
        Mockito.when(ACCOUNT_DAO.findByAccountNumber(transaction.getCreditedAccount())).thenReturn(creditedAccount);

        PostTransfer postTransfer = TRANSACTION_SERVICE.transfer(transaction);
        Assertions.assertEquals(postTransfer.debitedAccount.getBalance(), BigDecimal.valueOf(90));
        Assertions.assertEquals(postTransfer.creditedAccount.getBalance(), BigDecimal.valueOf(110));
    }

}
