package com.transaction.app.infra.repositories;

import com.transactions.domain.entities.Merchant;
import com.transactions.domain.ports.MerchantRepository;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DbMerchantRepository implements MerchantRepository {

    private final JdbcClient jdbcClient;

    public DbMerchantRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<Merchant> getMerchantByName(String name) {
        return jdbcClient.sql("SELECT * FROM merchant WHERE name = ?")
                .param(name)
                .query(Merchant.class)
                .optional();
    }
}
