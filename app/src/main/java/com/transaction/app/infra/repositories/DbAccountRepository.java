package com.transaction.app.infra.repositories;

import com.transactions.domain.entities.Account;
import com.transactions.domain.entities.BalanceAccount;
import com.transactions.domain.ports.AccountRepository;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DbAccountRepository implements AccountRepository {

    private final JdbcClient jdbcClient;

    public DbAccountRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<Account> getAccount(String accountId) {
        return jdbcClient.sql("SELECT * FROM account WHERE id = ?")
                .param(UUID.fromString(accountId))
                .query(Account.class)
                .optional();
    }

    @Override
    public Optional<BalanceAccount> getBalanceAccountByCategoryAndAccount(String accountId, String category) {
        return jdbcClient.sql("SELECT * FROM balanceaccount WHERE accountid = ? AND category = ?")
                .param(UUID.fromString(accountId))
                .param(category)
                .query(BalanceAccount.class)
                .optional();
    }

    @Override
    public void updateBalanceAccount(String balanceAccountId, Integer newBalanceCents) {
        jdbcClient.sql("UPDATE balanceaccount SET balancecents = ? WHERE id = ?")
                .param(newBalanceCents)
                .param(UUID.fromString(balanceAccountId))
                .update();
    }
}
