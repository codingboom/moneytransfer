package com.moneytransfer.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Transaction {
    @JsonProperty("debited_account")
    private String debitedAccount;

    @JsonProperty("credited_account")
    private String creditedAccount;

    @JsonProperty("transfer_amount")
    private BigDecimal transferAmount;

    public String getDebitedAccount() {
        return this.debitedAccount;
    }

    public String getCreditedAccount() {
        return this.creditedAccount;
    }

    public BigDecimal getTransferAmount() {
        return this.transferAmount;
    }

    public void setDebitedAccount(String debitedAccount) {
        this.debitedAccount = debitedAccount;
    }

    public void setCreditedAccount(String creditedAccount) {
        this.creditedAccount = creditedAccount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}
