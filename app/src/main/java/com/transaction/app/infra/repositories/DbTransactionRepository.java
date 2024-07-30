package com.transaction.app.infra.repositories;

import com.transactions.domain.entities.Transaction;
import com.transactions.domain.ports.TransactionRepository;

import org.springframework.stereotype.Repository;

@Repository
public class DbTransactionRepository implements TransactionRepository {
    @Override
    public void createTransaction(Transaction transaction) {

    }
}
