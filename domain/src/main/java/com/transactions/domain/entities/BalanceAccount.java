package com.transactions.domain.entities;

import com.transactions.domain.entities.enums.BalanceCategory;

public record BalanceAccount(
    String id,
    String accountId,
    BalanceCategory category,
    Integer balanceCents
) {}
