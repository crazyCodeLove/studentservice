package com.sse.model.page;

import com.sse.exception.PageSizeOverFlowException;
import com.sse.model.param.RequestParamBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.sse.constant.RequestConstant.Page.*;

/**
 * <p>
 * 分页参数
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-20 17:10
 */

@ToString(callSuper = true)
@Getter
@Setter
public class PageSortParam extends RequestParamBase {
    /**
     * 页号
     */
    protected int pageNum;

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

    public PageSortParam(int pageNum, int pageSize, String sort) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    @Override
    public void validParamInParam() {
        super.validParamInParam();
        if (pageSize > MAX_PAGE_SIZE) {
            throw new PageSizeOverFlowException("The number of single page exceeds the maximum:" + MAX_PAGE_SIZE);
        }
    }

    @Override
    public void preHandle() {
        super.preHandle();
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNum <= 0) {
            this.pageNum = DEFAULT_PAGE_NUM;
        }
    }
}
