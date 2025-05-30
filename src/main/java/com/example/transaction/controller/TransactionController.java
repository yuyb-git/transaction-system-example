package com.example.transaction.controller;

import com.example.transaction.entity.Transaction;
import com.example.transaction.global.R;
import com.example.transaction.service.TransactionService;
import com.example.transaction.utils.PageUtils;
import com.example.transaction.validation.group.UpdateValid;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Resource
    private TransactionService transactionService;

    /**
     * 创建交易记录
     *
     * @param transaction 交易记录
     * @return 创建成功的交易记录
     */
    @PostMapping("/createTransaction")
    public R<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return R.ok(transactionService.saveTransaction(transaction));
    }

    /**
     * 更新交易记录
     *
     * @param updatedTransaction 更新的交易记录
     * @return 更新后的交易记录
     */
    @PostMapping("/updateTransaction")
    public R<Transaction> updateTransaction(@Validated(UpdateValid.class) @RequestBody Transaction updatedTransaction) {
        return R.ok(transactionService.updateTransaction(updatedTransaction));
    }

    /**
     * 删除交易记录
     *
     * @param id 交易记录的ID
     * @return 删除结果
     */
    @PostMapping("/deleteTransaction")
    public R deleteTransaction(@RequestParam Long id) {
        transactionService.deleteTransactionById(id);
        return R.ok();
    }

    /**
     * 获取交易记录列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return 交易记录列表
     */
    @GetMapping("/getTransactionList")
    public R<PageInfo> getTransactionList(@RequestParam int page, @RequestParam int size) {
        List<Transaction> transactionList = transactionService.getTransactionList();
        return R.ok(PageUtils.returnPagingInfo(transactionList, page, size));
    }

    /**
     * 获取指定ID的交易记录
     *
     * @param id 交易记录的ID
     * @return 交易记录
     */
    @GetMapping("/findTransactionById")
    public R<Transaction> findTransactionById(@RequestParam Long id) {
        return R.ok(transactionService.findTransactionById(id));
    }

}
