package com.transactions.domain.entities;

import java.time.LocalDateTime;

public record Transaction(
    String id,
    String accountId,
    String merchantName,
    String mcc,
    Integer amountCents,
    LocalDateTime createdAt
) {}
