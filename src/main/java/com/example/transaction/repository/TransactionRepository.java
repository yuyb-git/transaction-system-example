package com.example.transaction.repository;

import com.example.transaction.entity.Transaction;
import com.example.transaction.utils.IdUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

@Repository
public class TransactionRepository {

    private final ConcurrentSkipListMap<Long, Transaction> transactionMap = new ConcurrentSkipListMap<>();

    public Transaction saveTransaction(Transaction transaction) {
        transaction.setId(IdUtils.getId());
        transactionMap.put(transaction.getId(), transaction);
        return transaction;
    }

    public Transaction updateTransaction(Transaction transaction) {
        transactionMap.put(transaction.getId(), transaction);
        return transaction;
    }

    public Transaction findById(Long id) {
        return transactionMap.get(id);
    }

    public List<Transaction> getTransactionList() {
        return new ArrayList<>(transactionMap.values());
    }

    public void delete(Long id) {
        transactionMap.remove(id);
    }

}
