package com.example.transaction.service.impl;

import com.example.transaction.entity.Transaction;
import com.example.transaction.exception.TransactionException;
import com.example.transaction.global.GlobalConstant;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @CachePut(value = GlobalConstant.CACHE_KEY_LIST, key = "#transaction.id")
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.saveTransaction(transaction);
    }

    @Override
    @Cacheable(value = GlobalConstant.CACHE_KEY, key = "#id")
    public Transaction findTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id);
        if(transaction == null) {
            throw new TransactionException("未找到交易记录！");
        }
        return transaction;
    }


    @Override
    @CachePut(value = { GlobalConstant.CACHE_KEY, GlobalConstant.CACHE_KEY_LIST}, key = "#id")
    public void deleteTransactionById(Long id) {
        transactionRepository.delete(id);
    }

    @Override
    @CachePut(value = { GlobalConstant.CACHE_KEY, GlobalConstant.CACHE_KEY_LIST}, key = "#transaction.id")
    public Transaction updateTransaction(Transaction transaction) {
        Transaction transactionData = transactionRepository.findById(transaction.getId());
        if(transactionData == null) {
            throw new TransactionException("未找到交易记录！");
        }
        return transactionRepository.updateTransaction(transaction);
    }

    @Override
    @Cacheable(value = GlobalConstant.CACHE_KEY_LIST)
    public List<Transaction> getTransactionList() {
        return transactionRepository.getTransactionList();
    }

}
