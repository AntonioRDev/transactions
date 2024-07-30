package com.transaction.app.api.controllers.dto;

import com.transactions.domain.usecases.dto.AuthorizeTransactionRequest;
import jakarta.validation.constraints.NotNull;

public class AuthorizeTransactionRequestDto {
    @NotNull
    public String accountId;
    @NotNull
    public String merchantName;
    @NotNull
    public String mcc;
    @NotNull
    public Integer amountCents;

    public static AuthorizeTransactionRequest toDomainRequest(AuthorizeTransactionRequestDto dto) {
        return new AuthorizeTransactionRequest(
                dto.accountId,
                dto.merchantName,
                dto.mcc,
                dto.amountCents
        );
    }
}
