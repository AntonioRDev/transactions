package com.transactions.domain.ports;

import com.transactions.domain.entities.Merchant;

public interface MerchantRepository {
    Merchant getMerchantByName(String name);
}
