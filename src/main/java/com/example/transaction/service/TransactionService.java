package com.example.transaction.service;

import com.example.transaction.entity.Transaction;

import java.util.List;

public interface TransactionService {

    /**
     * 保存交易信息
     * @param transaction
     * @return
     */
    Transaction saveTransaction(Transaction transaction);
    /**
     * 根据id查询交易信息
     * @param id
     * @return
     */
    Transaction findTransactionById(Long id);
    /**
     * 根据id删除交易信息
     * @param id
     */
    void deleteTransactionById(Long id);
    /**
     * 更新交易信息
     * @param transaction
     * @return
     */
    Transaction updateTransaction(Transaction transaction);
    /**
     * 查询所有交易信息
     * @return
     */
    List<Transaction> getTransactionList();

}
