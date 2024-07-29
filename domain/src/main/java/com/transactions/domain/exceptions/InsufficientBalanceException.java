package com.transactions.domain.exceptions;

import com.transactions.domain.entities.enums.BalanceCategory;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String accountId, String category) {
        super("Insufficient balance for account: " + accountId + " and category: " + category);
    }
}
