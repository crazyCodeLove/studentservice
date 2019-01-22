package com.sse.model.page;

import com.sse.exception.PageSizeOverFlowException;
import com.sse.model.param.RequestParamBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页参数
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-20 17:10
 */

@ToString(callSuper = true)
@Getter
@Setter
public class PageSortParam extends RequestParamBase {

    /**
     * 请求单页最大数据量为 1000 个
     */
    private static final int MAX_PAGE_SIZE = 1000;
    /**
     * 默认当前页
     */
    private static final int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认单页数量
     */
    private static final int DEFAULT_PAGE_SIZE = 20;


    /**
     * 页号
     */
    protected int currentPage;

    /**
     * 每页条数
     */
    protected int pageSize;

    /**
     * 排序字段 规则（升序 asc、降序 desc）。例如：按照 username 升序， "username asc"
     */
    protected String sort;

    public PageSortParam() {
    }

    public PageSortParam(int currentPage, int pageSize, String sort) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    @Override
    public void validParamInParam() {
        if (pageSize > MAX_PAGE_SIZE) {
            throw new PageSizeOverFlowException("单页数量超过了最大值：" + MAX_PAGE_SIZE);
        }
        super.validParamInParam();
    }

    @Override
    public void preHandle() {
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            this.currentPage = DEFAULT_CURRENT_PAGE;
        }
        super.preHandle();
    }
}
