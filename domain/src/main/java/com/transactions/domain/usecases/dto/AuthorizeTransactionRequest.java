package com.transactions.domain.usecases.dto;

public record AuthorizeTransactionRequest(
        String accountId,
        String merchantName,
        String mcc,
        Integer amountCents
) {}
