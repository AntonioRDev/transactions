package com.transactions.domain.usecases;

import com.transactions.domain.entities.enums.BalanceCategory;
import com.transactions.domain.exceptions.AccountNotFoundException;
import com.transactions.domain.exceptions.BalanceAccountNotFoundException;
import com.transactions.domain.exceptions.ConcurrencyException;
import com.transactions.domain.exceptions.InsufficientBalanceException;
import com.transactions.domain.ports.AccountRepository;
import com.transactions.domain.ports.MerchantRepository;
import com.transactions.domain.ports.TransactionRepository;
import com.transactions.domain.usecases.mocks.AccountFactory;
import com.transactions.domain.usecases.mocks.BalanceAccountFactory;
import com.transactions.domain.usecases.mocks.MerchantFactory;
import com.transactions.domain.usecases.mocks.TransactionRequestFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizeTransactionUseCaseTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MerchantRepository merchantRepository;

    @InjectMocks
    private AuthorizeTransactionUseCase sut;

    @Test
    void shouldThrowIfAccountNotFound() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> sut.execute(request));
    }

    @Test
    void shouldMapFoodCategoryCorrectly() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var account = AccountFactory.createAccount();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(account));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.empty());
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(Optional.empty());

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(account.id()), eq(BalanceCategory.FOOD.name()));
    }

    @Test
    void shouldMapMealCategoryCorrectly() {
        var request = TransactionRequestFactory.createRequestWithMealMcc();
        var account = AccountFactory.createAccount();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(account));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.empty());
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(Optional.empty());

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(account.id()), eq(BalanceCategory.MEAL.name()));
    }

    @Test
    void shouldMapCashIfNoMerchantFoundAndNoMccFound() {
        var request = TransactionRequestFactory.createRequestWithNonMappedMcc();
        var account = AccountFactory.createAccount();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(account));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.empty());
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(Optional.empty());

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(account.id()), eq(BalanceCategory.CASH.name()));
    }

    @Test
    void shouldMapMerchantWithFoodMccCorrectly() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        var account = AccountFactory.createAccount();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(account));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(foodMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(Optional.empty());

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(account.id()), eq(BalanceCategory.FOOD.name()));
    }

    @Test
    void shouldMapMerchantWithMealMccCorrectly() {
        var request = TransactionRequestFactory.createRequestWithMealMcc();
        var mealMerchant = MerchantFactory.createMerchantWithMealMcc();
        var account = AccountFactory.createAccount();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(account));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(mealMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(Optional.empty());

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(account.id()), eq(BalanceCategory.MEAL.name()));
    }

    @Test
    void shouldMapMerchantWithNonMappedMccCorrectly() {
        var request = TransactionRequestFactory.createRequestWithNonMappedMcc();
        var nonMappedMerchant = MerchantFactory.createMerchantWithNonMappedMcc();
        var account = AccountFactory.createAccount();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(account));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(nonMappedMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(Optional.empty());

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(account.id()), eq(BalanceCategory.CASH.name()));
    }

    @Test
    void shouldThrowIfBalanceAccountIsCashAndHaveInsufficientBalance() {
        var request = TransactionRequestFactory.createRequestWithNonMappedMcc();
        var nonMappedMerchant = MerchantFactory.createMerchantWithNonMappedMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(AccountFactory.createAccount()));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(nonMappedMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(Optional.of(BalanceAccountFactory.createBalanceAccountWithCashCategory(100)));

        assertThrows(InsufficientBalanceException.class, () -> sut.execute(request));
    }

    @Test
    void shouldThrowIfBalanceAccountIsNotCashAndHaveInsufficientBalance() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(AccountFactory.createAccount()));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(foodMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(Optional.of(BalanceAccountFactory.createBalanceAccountWithFoodCategory(100)))
                .thenReturn(Optional.empty());

        assertThrows(InsufficientBalanceException.class, () -> sut.execute(request));
    }

    @Test
    void shouldThrowIfBalanceAccountIsNotCashAndHaveInsufficientBalanceInCashAccountAndCategoryAccount() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(AccountFactory.createAccount()));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(foodMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(Optional.of(BalanceAccountFactory.createBalanceAccountWithFoodCategory(100)))
                .thenReturn(Optional.of(BalanceAccountFactory.createBalanceAccountWithCashCategory(100)));

        assertThrows(InsufficientBalanceException.class, () -> sut.execute(request));
    }

    @Test
    void shouldCreateTransactionAndUpdateBalanceIfBalanceAccountHasSufficientBalance() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        var foodBalanceAccount = BalanceAccountFactory.createBalanceAccountWithFoodCategory(10000);
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(AccountFactory.createAccount()));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(foodMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(Optional.of(foodBalanceAccount));
        doNothing().when(transactionRepository).createTransaction(any());
        when(accountRepository.updateBalanceAccount(any(), any(), any())).thenReturn(1);

        sut.execute(request);

        verify(transactionRepository, times(1)).createTransaction(any());
        verify(accountRepository).updateBalanceAccount(eq(foodBalanceAccount.id()), eq(10000 - request.amountCents()), anyInt());
    }

    @Test
    void shouldThrowIfUpdateBalanceAccountIsNotProcessed() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        var foodBalanceAccount = BalanceAccountFactory.createBalanceAccountWithFoodCategory(10000);
        when(accountRepository.getAccount(request.accountId())).thenReturn(Optional.of(AccountFactory.createAccount()));
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(Optional.of(foodMerchant));
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(Optional.of(foodBalanceAccount));
        doNothing().when(transactionRepository).createTransaction(any());
        when(accountRepository.updateBalanceAccount(any(), any(), any())).thenReturn(0);

        assertThrows(ConcurrencyException.class, () -> sut.execute(request));
    }
}