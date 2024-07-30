package com.transactions.domain.ports;

import com.transactions.domain.entities.Transaction;

public interface TransactionRepository {
    void createTransaction(Transaction transaction);
}
