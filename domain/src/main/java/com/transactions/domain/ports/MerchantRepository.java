package com.transactions.domain.ports;

import com.transactions.domain.entities.Merchant;

import java.util.Optional;

public interface MerchantRepository {
    Optional<Merchant> getMerchantByName(String name);
}
