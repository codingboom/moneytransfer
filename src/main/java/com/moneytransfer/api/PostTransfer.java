package com.moneytransfer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class PostTransfer {
    @JsonProperty("debited_account")
    Account debitedAccount;

    @JsonProperty("credited_account")
    Account creditedAccount;

    @JsonProperty("transfer_amount")
    BigDecimal transfer_amount;
}
