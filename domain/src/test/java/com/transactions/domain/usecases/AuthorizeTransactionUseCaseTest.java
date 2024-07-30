package com.transactions.domain.usecases;

import com.transactions.domain.entities.enums.BalanceCategory;
import com.transactions.domain.exceptions.AccountNotFoundException;
import com.transactions.domain.exceptions.BalanceAccountNotFoundException;
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
        when(accountRepository.getAccount(request.accountId())).thenReturn(null);

        assertThrows(AccountNotFoundException.class, () -> sut.execute(request));
    }

    @Test
    void shouldMapFoodCategoryCorrectly() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(null);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(null);

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(request.accountId()), eq(BalanceCategory.FOOD.name()));
    }

    @Test
    void shouldMapMealCategoryCorrectly() {
        var request = TransactionRequestFactory.createRequestWithMealMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(null);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(null);

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(request.accountId()), eq(BalanceCategory.MEAL.name()));
    }

    @Test
    void shouldMapCashIfNoMerchantFoundAndNoMccFound() {
        var request = TransactionRequestFactory.createRequestWithNonMappedMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(null);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(null);

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(request.accountId()), eq(BalanceCategory.CASH.name()));
    }

    @Test
    void shouldMapMerchantWithFoodMccCorrectly() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(foodMerchant);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(null);

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(request.accountId()), eq(BalanceCategory.FOOD.name()));
    }

    @Test
    void shouldMapMerchantWithMealMccCorrectly() {
        var request = TransactionRequestFactory.createRequestWithMealMcc();
        var mealMerchant = MerchantFactory.createMerchantWithMealMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(mealMerchant);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(null);

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(request.accountId()), eq(BalanceCategory.MEAL.name()));
    }

    @Test
    void shouldMapMerchantWithNonMappedMccCorrectly() {
        var request = TransactionRequestFactory.createRequestWithNonMappedMcc();
        var nonMappedMerchant = MerchantFactory.createMerchantWithNonMappedMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(nonMappedMerchant);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any())).thenReturn(null);

        assertThrows(BalanceAccountNotFoundException.class, () -> sut.execute(request));
        verify(accountRepository).getBalanceAccountByCategoryAndAccount(eq(request.accountId()), eq(BalanceCategory.CASH.name()));
    }

    @Test
    void shouldThrowIfBalanceAccountIsCashAndHaveInsufficientBalance() {
        var request = TransactionRequestFactory.createRequestWithNonMappedMcc();
        var nonMappedMerchant = MerchantFactory.createMerchantWithNonMappedMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(nonMappedMerchant);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(BalanceAccountFactory.createBalanceAccountWithCashCategory(100));

        assertThrows(InsufficientBalanceException.class, () -> sut.execute(request));
    }

    @Test
    void shouldThrowIfBalanceAccountIsNotCashAndHaveInsufficientBalance() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(foodMerchant);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(BalanceAccountFactory.createBalanceAccountWithFoodCategory(100))
                .thenReturn(null);

        assertThrows(InsufficientBalanceException.class, () -> sut.execute(request));
    }

    @Test
    void shouldThrowIfBalanceAccountIsNotCashAndHaveInsufficientBalanceInCashAccountAndCategoryAccount() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(foodMerchant);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(BalanceAccountFactory.createBalanceAccountWithFoodCategory(100))
                .thenReturn(BalanceAccountFactory.createBalanceAccountWithCashCategory(100));

        assertThrows(InsufficientBalanceException.class, () -> sut.execute(request));
    }

    @Test
    void shouldCreateTransactionAndUpdateBalanceIfBalanceAccountHasSufficientBalance() {
        var request = TransactionRequestFactory.createRequestWithFoodMcc();
        var foodMerchant = MerchantFactory.createMerchantWithFoodMcc();
        var foodBalanceAccount = BalanceAccountFactory.createBalanceAccountWithFoodCategory(10000);
        when(accountRepository.getAccount(request.accountId())).thenReturn(AccountFactory.createAccount());
        when(merchantRepository.getMerchantByName(request.merchantName())).thenReturn(foodMerchant);
        when(accountRepository.getBalanceAccountByCategoryAndAccount(any(), any()))
                .thenReturn(foodBalanceAccount);
        doNothing().when(transactionRepository).createTransaction(any());

        sut.execute(request);

        verify(transactionRepository, times(1)).createTransaction(any());
        verify(accountRepository).updateBalanceAccount(foodBalanceAccount.id(), 10000 - request.amountCents());
    }
}