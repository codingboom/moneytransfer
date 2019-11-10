package com.moneytransfer.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moneytransfer.exception.InsufficientBalanceException;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Account {

    @NotEmpty
    @JsonProperty("account_number")
    private String accountNumber;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @JsonProperty("balance")
    private BigDecimal balance;

    public Account(String accountNumber, BigDecimal balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public void deposit(BigDecimal depositAmount){
        validateAmount(depositAmount);
        setBalance(balance.add(depositAmount));
    }

    public void withdraw(BigDecimal withdrawAmount){
        validateAmount(withdrawAmount);
        if (balance.compareTo(withdrawAmount) < 0){
            throw new InsufficientBalanceException("Insufficient funds to withdraw");
        }
        setBalance(balance.subtract(withdrawAmount));
    }

    private void validateAmount(BigDecimal amount){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount should not be null and should be greater than zero");
        }
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static class AccountBuilder {
        private String accountNumber;
        private BigDecimal balance;

        AccountBuilder() {
        }

        public Account.AccountBuilder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Account.AccountBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Account build() {
            return new Account(accountNumber, balance);
        }

        public String toString() {
            return "Account.AccountBuilder(accountNumber=" + this.accountNumber + ", balance=" + this.balance + ")";
        }
    }
}
