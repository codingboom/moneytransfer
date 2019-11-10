package com.moneytransfer.db;

import com.moneytransfer.api.Account;
import com.moneytransfer.core.AccountDTO;
import com.moneytransfer.exception.AccountNotFoundException;
import com.moneytransfer.exception.ExistingAccountFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class AccountDAOTest {
    private static final AccountDAO ACCOUNT_DAO = Mockito.mock(AccountDAO.class);

    private Account account;
    private AccountDTO accountDTO;

    @BeforeEach
    public void setUp() {
        account = Account.builder()
                .accountNumber("12345")
                .balance(BigDecimal.valueOf(100))
                .build();

        accountDTO = AccountDTO.builder()
                .accountNumber("12345")
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    @AfterEach
    public void reset(){
        Mockito.reset(ACCOUNT_DAO);
    }

    @Test
    public void testAccountCreation(){
        Mockito.when(ACCOUNT_DAO.createAccount(account)).thenReturn(accountDTO);
        AccountDTO result = ACCOUNT_DAO.createAccount(account);
        Assertions.assertEquals(result.getAccountNumber(), account.getAccountNumber());
        Assertions.assertEquals(result.getBalance(), account.getBalance());
    }

    @Test
    public void testDuplicateAccountCreation(){
        Mockito.when(ACCOUNT_DAO.createAccount(account))
                .thenReturn(accountDTO)
                .thenThrow(new ExistingAccountFoundException("Account already exists"));

        Assertions.assertThrows(ExistingAccountFoundException.class, () -> {
            ACCOUNT_DAO.createAccount(account);
            ACCOUNT_DAO.createAccount(account);
        });
    }

    @Test
    public void testInvalidAccountNumber(){
        Mockito.when(ACCOUNT_DAO.createAccount(account)).thenCallRealMethod();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.setAccountNumber("1A2");
            ACCOUNT_DAO.createAccount(account);
        });
    }

    @Test
    public void testFindAccount(){
        Mockito.when(ACCOUNT_DAO.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
        Account result = ACCOUNT_DAO.findByAccountNumber(account.getAccountNumber());
        Assertions.assertEquals(result.getAccountNumber(), account.getAccountNumber());
        Assertions.assertEquals(result.getBalance(), account.getBalance());
    }

    @Test
    public void testAccountNotExistingCreation(){
        Mockito.when(ACCOUNT_DAO.findByAccountNumber(account.getAccountNumber()))
                .thenThrow(new AccountNotFoundException("Account does not exists"));

        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            ACCOUNT_DAO.findByAccountNumber(account.getAccountNumber());
        });
    }
}
