package com.transactions.app.api.controllers;

import com.transactions.app.api.controllers.dto.ApiResponse;
import com.transactions.app.api.controllers.dto.AuthorizeTransactionRequestDto;
import com.transactions.domain.usecases.AuthorizeTransactionUseCase;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public ApiResponse authorizeTransaction(@Valid @RequestBody AuthorizeTransactionRequestDto request) {
        authorizeTransactionUseCase.execute(AuthorizeTransactionRequestDto.toDomainRequest(request));
        return new ApiResponse("00");
    }
}
