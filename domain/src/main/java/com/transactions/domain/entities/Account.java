package com.transactions.domain.entities;

import java.util.List;

public record Account(
    String id,
    String personName,
    List<BalanceAccount> balanceAccounts
) {}