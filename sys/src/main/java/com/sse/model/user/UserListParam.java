package com.sse.model.user;

import com.sse.model.page.PageSortParam;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * author ZHAOPENGCHENG
 * date  2019-01-20 12:39
 */

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class UserListParam extends PageSortParam {
    private Long uid;
    private String username;
    private String email;
    private String telphone;
    private Date birthday;
    private Date createTime;
    private Date updateTime;

    @Builder(toBuilder = true)
    public UserListParam(Long uid,
                         String username,
                         String email,
                         String telphone,
                         Date birthday,
                         Date createTime,
                         Date updateTime,
                         int currentPage,
                         int pageSize,
                         String sort) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.telphone = telphone;
        this.birthday = birthday;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sort = sort;
    }


    @Override
    public void validParamInParam() {
        super.validParamInParam();
    }

    @Override
    public void preHandle() {
        // 默认按照 uid 排序
        if (StringUtils.isBlank(sort)) {
            sort = "uid desc";
        }
        super.preHandle();
    }
}
