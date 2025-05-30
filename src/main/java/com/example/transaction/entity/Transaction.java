package com.example.transaction.entity;

import com.example.transaction.validation.group.UpdateValid;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Transaction implements Serializable {

    /**
     * 交易id
     */
    @NotNull(message = "交易id不能为空", groups = UpdateValid.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 交易描述
     */
    @NotBlank(message = "Description is required")
    @Length(max = 200, message = "description length must be between 0 and 200")
    private String description;

    /**
     * 交易金额
     */
    @NotBlank(message = "交易金额不能为空")
    @DecimalMin(value = "0.01", inclusive = true, message = "交易金额必须大于0")
    @DecimalMax(value = "50000", inclusive = false, message = "单次交易金额不能超过50000")
    @Digits(integer = 5, fraction = 2, message = "交易金额需保留5位整数，2位小数")
    private BigDecimal amount;

    /**
     * 交易类型
     */
    private Integer transactionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }
}
