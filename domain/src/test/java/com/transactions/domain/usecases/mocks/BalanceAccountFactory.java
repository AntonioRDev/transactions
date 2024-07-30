package com.transactions.domain.usecases.mocks;

import com.transactions.domain.entities.BalanceAccount;
import com.transactions.domain.entities.enums.BalanceCategory;

public class BalanceAccountFactory {
    public static BalanceAccount createBalanceAccountWithFoodCategory(Integer balanceCents) {
        return new BalanceAccount(
                "balanceAccountId",
                "accountId",
                BalanceCategory.FOOD,
                balanceCents
        );
    }

    public static BalanceAccount createBalanceAccountWithMealCategory(Integer balanceCents) {
        return new BalanceAccount(
                "balanceAccountId",
                "accountId",
                BalanceCategory.MEAL,
                balanceCents
        );
    }

    public static BalanceAccount createBalanceAccountWithCashCategory(Integer balanceCents) {
        return new BalanceAccount(
                "balanceAccountId",
                "accountId",
                BalanceCategory.CASH,
                balanceCents
        );
    }
}
