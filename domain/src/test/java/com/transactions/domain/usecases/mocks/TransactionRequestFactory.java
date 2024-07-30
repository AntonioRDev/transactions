package com.transactions.domain.usecases.mocks;

import com.transactions.domain.usecases.dto.AuthorizeTransactionRequest;

public class TransactionRequestFactory {
    public static AuthorizeTransactionRequest createRequestWithFoodMcc() {
        return new AuthorizeTransactionRequest(
                "accountId",
                "SUPERMERCADOS BH",
                "5412",
                1750);
    }

    public static AuthorizeTransactionRequest createRequestWithMealMcc() {
        return new AuthorizeTransactionRequest(
                "accountId",
                "UBER EATS",
                "5811",
                1000);
    }

    public static AuthorizeTransactionRequest createRequestWithNonMappedMcc() {
        return new AuthorizeTransactionRequest(
                "accountId",
                "POSTO IPIRANGA",
                "5541",
                5000);
    }
}
