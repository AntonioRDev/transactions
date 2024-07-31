package com.transactions.domain.exceptions;

public class BalanceAccountNotFoundException extends DomainException {
    public BalanceAccountNotFoundException(String accountId, String category) {
        super("Balance account not found for: " + accountId + " and category: " + category);
    }
}
