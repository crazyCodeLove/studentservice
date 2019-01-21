package com.sse.model.param;

/**
 * author ZHAOPENGCHENG
 * date  2019-01-20 17:10
 */

public class PageSortParam extends RequestParamBase {

    /**
     * 页号
     */
    protected int currentPage;

    /**
     * 每页条数
     */
    protected int pageSize;

    /**
     * 排序字段
     */
    protected String sort;



}
