package com.transactions.domain.entities;

public record Transaction(
    String id,
    String accountId,
    String merchantName,
    String mcc,
    Integer amountCents
) {}
