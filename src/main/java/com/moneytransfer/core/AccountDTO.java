package com.moneytransfer.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class AccountDTO {
    @Id
    @NotEmpty
    @Column(name = "account_number")
    private String accountNumber;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "balance")
    private BigDecimal balance;

    public AccountDTO(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public AccountDTO() {
    }

    public static AccountDTOBuilder builder() {
        return new AccountDTOBuilder();
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public static class AccountDTOBuilder {
        private String accountNumber;
        private BigDecimal balance;

        AccountDTOBuilder() {
        }

        public AccountDTO.AccountDTOBuilder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public AccountDTO.AccountDTOBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public AccountDTO build() {
            return new AccountDTO(accountNumber, balance);
        }

        public String toString() {
            return "AccountDTO.AccountDTOBuilder(accountNumber=" + this.accountNumber + ", balance=" + this.balance + ")";
        }
    }
}
