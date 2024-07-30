package com.transactions.domain.usecases;

import com.transactions.domain.entities.Account;
import com.transactions.domain.entities.BalanceAccount;
import com.transactions.domain.entities.Transaction;
import com.transactions.domain.entities.enums.BalanceCategory;
import com.transactions.domain.exceptions.AccountNotFoundException;
import com.transactions.domain.exceptions.BalanceAccountNotFoundException;
import com.transactions.domain.exceptions.InsufficientBalanceException;
import com.transactions.domain.ports.AccountRepository;
import com.transactions.domain.ports.TransactionRepository;
import com.transactions.domain.usecases.dto.AuthorizeTransactionRequest;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Map;
import java.util.UUID;

@Named
public class AuthorizeTransactionUseCase {
    private final Map<String, BalanceCategory> mccToCategoryMap = Map.of(
            "5411", BalanceCategory.FOOD,
            "5412", BalanceCategory.FOOD,
            "5812", BalanceCategory.MEAL,
            "5811", BalanceCategory.MEAL
    );

    @Inject
    private final AccountRepository accountRepository;
    @Inject
    private final TransactionRepository transactionRepository;

    public AuthorizeTransactionUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void execute(AuthorizeTransactionRequest request) {
        Account account = accountRepository.getAccount(request.accountId());
        if (account == null) {
            throw new AccountNotFoundException(request.accountId());
        }

        BalanceCategory transactionCategory = BalanceCategory.CASH;
        if (mccToCategoryMap.containsKey(request.mcc())) {
            transactionCategory = mccToCategoryMap.get(request.mcc());
        }

        BalanceAccount balanceAccount = accountRepository.getBalanceAccountByCategoryAndAccount(request.accountId(), transactionCategory.name());
        if (balanceAccount == null) {
            throw new BalanceAccountNotFoundException(request.accountId(), transactionCategory.name());
        }

        if (balanceAccount.balanceCents < request.amountCents()) {
            if (balanceAccount.category == BalanceCategory.CASH) {
                throw new InsufficientBalanceException(request.accountId(), transactionCategory.name());
            }

            BalanceAccount cashBalanceAccount = accountRepository.getBalanceAccountByCategoryAndAccount(request.accountId(), BalanceCategory.CASH.name());
            if (cashBalanceAccount == null) {
                throw new BalanceAccountNotFoundException(request.accountId(), BalanceCategory.CASH.name());
            }

            if (cashBalanceAccount.balanceCents < request.amountCents()) {
                throw new InsufficientBalanceException(request.accountId(), transactionCategory.name());
            }

            balanceAccount = cashBalanceAccount;
        }

        Transaction transaction = new Transaction();
        transaction.id = UUID.randomUUID().toString();
        transaction.accountId = request.accountId();
        transaction.merchantName = request.merchantName();
        transaction.mcc = request.mcc();
        transaction.amountCents = request.amountCents();

        transactionRepository.createTransaction(transaction);

        Integer newBalanceCents = balanceAccount.balanceCents - request.amountCents();
        accountRepository.updateBalanceAccount(balanceAccount.id, newBalanceCents);
    }
}
