package com.transaction.app.infra.repositories;

import com.transactions.domain.entities.Account;
import com.transactions.domain.entities.BalanceAccount;
import com.transactions.domain.ports.AccountRepository;

import org.springframework.stereotype.Repository;

@Repository
public class DbAccountRepository implements AccountRepository {
    @Override
    public Account getAccount(String accountId) {
        return null;
    }

    @Override
    public BalanceAccount getBalanceAccountByCategoryAndAccount(String accountId, String category) {
        return null;
    }

    @Override
    public void updateBalanceAccount(String balanceAccountId, Integer newBalanceCents) {

    }
}
