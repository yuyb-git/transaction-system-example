package com.example.transaction.global;

public class GlobalConstant {

    /** 请求成功 */
    public static final int SUCCESS = 200;

    /** 请求失败 */
    public static final int FAIL = -1;

    /** 缺少必要参数 */
    public static final String MISSED_REQUIRED_PARAM_ERROR = "缺少必要参数:%s";

    /** 缓存key */
    public static final String CACHE_KEY = "transactions";
    /** 缓存key */
    public static final String CACHE_KEY_LIST = "transactionList";
}
