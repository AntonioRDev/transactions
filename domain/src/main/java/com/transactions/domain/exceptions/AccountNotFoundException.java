package com.transactions.domain.exceptions;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException(String accountId) {
        super("Account not found: " + accountId);
    }
}
