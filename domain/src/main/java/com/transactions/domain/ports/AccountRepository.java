package com.transactions.domain.ports;

import com.transactions.domain.entities.Account;
import com.transactions.domain.entities.BalanceAccount;

public interface AccountRepository {
    Account getAccount(String accountId);
    BalanceAccount getBalanceAccountByCategoryAndAccount(String accountId, String category);
    void updateBalanceAccount(String balanceAccountId, Integer newBalanceCents);
}
