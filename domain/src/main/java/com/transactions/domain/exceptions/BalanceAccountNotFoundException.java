package com.transactions.domain.exceptions;

import com.transactions.domain.entities.enums.BalanceCategory;

public class BalanceAccountNotFoundException extends RuntimeException {
    public BalanceAccountNotFoundException(String accountId, String category) {
        super("Balance account not found for: " + accountId + " and category: " + category);
    }
}
