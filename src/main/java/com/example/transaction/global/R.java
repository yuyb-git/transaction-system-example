/*
 *
 *      Copyright (c) 2018-2025, supervise All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: supervise
 *
 */

package com.example.transaction.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> R<T> ok() {
        return restResult(null, GlobalConstant.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, GlobalConstant.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, GlobalConstant.SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, GlobalConstant.FAIL, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, GlobalConstant.FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, GlobalConstant.FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, GlobalConstant.FAIL, msg);
    }

    static <T> R<T> restResult(T data, int code, String msg) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

}
