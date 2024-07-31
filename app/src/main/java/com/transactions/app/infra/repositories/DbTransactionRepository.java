package com.transactions.app.infra.repositories;

import com.transactions.domain.entities.Transaction;
import com.transactions.domain.ports.TransactionRepository;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DbTransactionRepository implements TransactionRepository {

    private final JdbcClient jdbcClient;

    public DbTransactionRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        jdbcClient.sql("INSERT INTO transaction (id, accountid, merchantname, mcc, amountcents, createdat) VALUES (?, ?, ?, ?, ?, ?)")
                .param(UUID.fromString(transaction.id()))
                .param(UUID.fromString(transaction.accountId()))
                .param(transaction.merchantName())
                .param(transaction.mcc())
                .param(transaction.amountCents())
                .param(transaction.createdAt())
                .update();
    }
}
