package com.transaction.app.infra.repositories;

import com.transactions.domain.entities.Merchant;
import com.transactions.domain.ports.MerchantRepository;

import org.springframework.stereotype.Repository;

@Repository
public class DbMerchantRepository implements MerchantRepository {
    @Override
    public Merchant getMerchantByName(String name) {
        return null;
    }
}
