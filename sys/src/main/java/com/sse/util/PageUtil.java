package com.sse.util;

import com.github.pagehelper.Page;
import com.sse.model.page.PageResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-22 20:52
 */

public class PageUtil {

    /**
     * 将page对象转为map
     *
     * @param page PageHelper 请求返回结果
     * @return <code>
     * Map<String, Object> map. map 当中必含有 page 和 list字段 <br/>
     * page 字段表示分页信息， list 字段表示当前页数据
     * <code/>
     */
    public static Map<String, Object> toResultMap(Page<?> page) {
        Map<String, Object> resultMap = new LinkedHashMap<>(4);
        resultMap.put("page", PageResult.builder().currentPage(page.getPageNum()).pageSize(page.size()).total(page.getTotal()).build());
        resultMap.put("list", page.getResult());
        return resultMap;
    }

}
