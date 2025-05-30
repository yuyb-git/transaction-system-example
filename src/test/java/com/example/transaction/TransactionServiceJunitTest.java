package com.example.transaction;

import com.example.transaction.entity.Transaction;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest(classes = TransactionSystemExampleApplication.class)
public class TransactionServiceJunitTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionServiceImpl transactionService;

    private Transaction testTransaction;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImpl(transactionRepository);
        testTransaction = new Transaction();
        testTransaction.setId(1L);
        testTransaction.setDescription("Test Transaction");
        testTransaction.setAmount(new BigDecimal("100.00"));
    }

    @Test
    public void testCreateTransaction() {
        when(transactionRepository.saveTransaction(any(Transaction.class))).thenReturn(testTransaction);
        Transaction newTransaction = transactionService.saveTransaction(testTransaction);
        assertNotNull(newTransaction.getId());
        assertEquals("Test Transaction", newTransaction.getDescription());
        assertEquals(new BigDecimal("100.00"), newTransaction.getAmount());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction createdTransaction = transactionService.saveTransaction(testTransaction);
        Long id = testTransaction.getId();

        transactionService.deleteTransactionById(id);
        assertFalse(transactionService.getTransactionList().stream().anyMatch(t -> t.getId().equals(id)));
    }

    @Test
    public void testModifyTransaction() {
        Transaction createdTransaction = transactionService.saveTransaction(testTransaction);
        Transaction modifiedTransaction = transactionService.updateTransaction(testTransaction);
        assertEquals("Updated Transaction", modifiedTransaction.getDescription());
        assertEquals(new BigDecimal("200.00"), modifiedTransaction.getAmount());
    }

    @Test
    public void testListAllTransactions() {
        when(transactionRepository.getTransactionList()).thenReturn(Arrays.asList(testTransaction));
        List<Transaction> list = transactionService.getTransactionList();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

}
