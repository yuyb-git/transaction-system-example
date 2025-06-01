package com.example.transaction.utils;

import com.github.pagehelper.PageInfo;
import java.util.List;

public class PageUtils {

    public static PageInfo returnPagingInfo(List transactionList, Integer page, Integer size) {
        //总记录数
        int totalCount = transactionList.size();
        //截取的开始位置
        int pageStart = page == 1 ? 0 : (page - 1) * size;
        //截取的结束位置
        int pageEnd = Math.min(totalCount, page * size);
        //分页后list
        List subList = transactionList.subList(pageStart, pageEnd);
        PageInfo pageInfo = new PageInfo(subList);
        pageInfo.setTotal(totalCount);
        pageInfo.setPageSize(size);
        pageInfo.setPageNum(page);
        pageInfo.setPages((int) Math.ceil((double) totalCount / size));
        return pageInfo;
    }


}