package com.transactions.domain.entities;

import com.transactions.domain.entities.enums.BalanceCategory;

public class BalanceAccount {
    public String id;
    public String accountId;
    public BalanceCategory category;
    public Integer balanceCents;
}
