package com.sse.model.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-22 20:48
 */

@Builder
@Getter
@Setter
@ToString
public class PageResult {
    /**
     * 页号
     */
    private int currentPage;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总条数
     */
    private long total;
}
