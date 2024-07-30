package com.transactions.domain.usecases.mocks;

import com.transactions.domain.entities.Account;

import java.util.ArrayList;

public class AccountFactory {
    public static Account createAccount() {
        return new Account(
            "1",
            "John Doe",
            new ArrayList<>()
        );
    }
}
