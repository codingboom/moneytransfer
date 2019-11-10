package com.moneytransfer.db;

import com.moneytransfer.api.Account;
import com.moneytransfer.core.AccountDTO;
import com.moneytransfer.exception.AccountNotFoundException;
import com.moneytransfer.exception.ExistingAccountFoundException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountDAO extends AbstractDAO<AccountDTO> {

    public AccountDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Account findByAccountNumber(String accountNumber){
        AccountDTO accountDTO = get(accountNumber);
        if(Objects.isNull(accountDTO)){
            throw new AccountNotFoundException("Account with account number \"" + accountNumber +"\" not found.");
        }
        return toAccount(accountDTO);
    }

    public AccountDTO createAccount(Account account) {
        Pattern regex = Pattern.compile("^[0-9]");
        Matcher matcher = regex.matcher(account.getAccountNumber());

        if(!matcher.matches()){
            throw new IllegalArgumentException("Account number should only contain digits");
        }

        AccountDTO accountDTO = get(account.getAccountNumber());
        if(!Objects.isNull(accountDTO)){
            throw new ExistingAccountFoundException("Account already exists.");
        }
        return persist(toDTO(account));
    }

    public void updateAccount(Account account){
        currentSession().merge(toDTO(account));
    }

    private AccountDTO toDTO(Account account){
        return AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build();
    }

    private Account toAccount(AccountDTO accountDTO){
        return Account.builder()
                .accountNumber(accountDTO.getAccountNumber())
                .balance(accountDTO.getBalance())
                .build();
    }
}
