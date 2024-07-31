package com.transactions.domain.exceptions;

public class InsufficientBalanceException extends DomainException {
    public InsufficientBalanceException(String accountId, String category) {
        super("Insufficient balance for account: " + accountId + " and category: " + category);
    }
}
