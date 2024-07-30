package com.transactions.domain.usecases.mocks;

import com.transactions.domain.entities.Merchant;

public class MerchantFactory {
    public static Merchant createMerchantWithFoodMcc() {
        return new Merchant(
            "1",
            "SUPERMERCADOS BH",
            "5412"
        );
    }

    public static Merchant createMerchantWithMealMcc() {
        return new Merchant(
            "2",
            "UBER EATS",
            "5812"
        );
    }

    public static Merchant createMerchantWithNonMappedMcc() {
        return new Merchant(
            "3",
            "Posto Teste",
            "1234"
        );
    }
}
