package com.sse.constant;

/**
 * <p>
 * 请求参数中常见常量设置
 * </p>
 * author pczhao  <br/>
 * date  2019-01-23 8:29
 */

public interface RequestConstant {

    /**
     * 分页请求中常量设置
     */
    class Page {

        /**
         * 请求单页最大数据量为 1000 个
         */
        public static final int MAX_PAGE_SIZE = 1000;

        /**
         * 默认当前页
         */
        public static final int DEFAULT_CURRENT_PAGE = 1;

        /**
         * 默认单页数量
         */
        public static final int DEFAULT_PAGE_SIZE = 20;
    }

}
