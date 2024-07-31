package com.transactions.domain.ports;

import com.transactions.domain.entities.Account;
import com.transactions.domain.entities.BalanceAccount;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> getAccount(String accountId);
    Optional<BalanceAccount> getBalanceAccountByCategoryAndAccount(String accountId, String category);
    void updateBalanceAccount(String balanceAccountId, Integer newBalanceCents);
}
