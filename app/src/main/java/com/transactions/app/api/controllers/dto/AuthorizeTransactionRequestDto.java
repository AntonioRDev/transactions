package com.transactions.app.api.controllers.dto;

import com.transactions.domain.usecases.dto.AuthorizeTransactionRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthorizeTransactionRequestDto {
    @NotBlank
    public String accountId;
    @NotBlank
    public String merchantName;
    @NotBlank
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
