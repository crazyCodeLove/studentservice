package com.sse.util;

import com.github.pagehelper.Page;

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
     * <code/>
     */
    public static Map<String, Object> toResultMap(Page<?> page) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        // 页码
        resultMap.put("pageNum", page.getPageNum());
        // 页面大小
        resultMap.put("pageSize", page.getPageSize());
        // 当前页数量
        resultMap.put("size", page.size());
        // 结果集数量
        resultMap.put("total", page.getTotal());
        // 总页数
        resultMap.put("pages", page.getPages());
        // 当前页结果
        resultMap.put("result", page.getResult());
        return resultMap;
    }

}
