package com.moneytransfer.api;

import com.moneytransfer.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        account = Account.builder()
                .accountNumber("12345")
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    @Test
    public void successfulWithdrawalTest(){
        account.withdraw(BigDecimal.TEN);
        Assertions.assertEquals(account.getBalance(), BigDecimal.valueOf(90));
    }

    @Test
    public void successfulDepositTest(){
        account.deposit(BigDecimal.TEN);
        Assertions.assertEquals(account.getBalance(), BigDecimal.valueOf(110));
    }

    @Test
    public void nullWithdrawalAmount(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(null);
        });
    }

    @Test
    public void negativeWithdrawalAmount(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(BigDecimal.valueOf(-1));
        });
    }

    @Test
    public void insufficientBalanceTest(){
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            account.withdraw(BigDecimal.valueOf(101));
        });
    }

    @Test
    public void nullDepositAmount(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(null);
        });
    }

    @Test
    public void negativeDepositAmount(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(BigDecimal.valueOf(-1));
        });
    }
}
