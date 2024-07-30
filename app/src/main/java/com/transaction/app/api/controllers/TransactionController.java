package com.transaction.app.api.controllers;

import com.transaction.app.api.controllers.dto.AuthorizeTransactionRequestDto;
import com.transactions.domain.usecases.AuthorizeTransactionUseCase;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final AuthorizeTransactionUseCase authorizeTransactionUseCase;

    public TransactionController(AuthorizeTransactionUseCase authorizeTransactionUseCase) {
        this.authorizeTransactionUseCase = authorizeTransactionUseCase;
    }

    @PostMapping("/authorize")
    @ResponseStatus(HttpStatus.OK)
    public void authorizeTransaction(@Valid @RequestBody AuthorizeTransactionRequestDto request) {
        authorizeTransactionUseCase.execute(AuthorizeTransactionRequestDto.toDomainRequest(request));
    }
}
