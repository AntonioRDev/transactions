package com.transactions.domain.exceptions;

public class ConcurrencyException extends DomainException {
    public ConcurrencyException(String accountId) {
        super("Another transaction was processed concurrently for account: " + accountId + ". Please try again.");
    }
}
