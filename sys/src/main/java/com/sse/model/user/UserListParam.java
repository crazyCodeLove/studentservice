package com.sse.model.user;

import com.sse.model.page.PageSortParam;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * author ZHAOPENGCHENG <br/>
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
                         int pageNum,
                         int pageSize,
                         String sort) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.telphone = telphone;
        this.birthday = birthday;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sort = sort;
    }


    @Override
    public void validParamInParam() {
        super.validParamInParam();
    }

    @Override
    public void preHandle() {
        super.preHandle();
        // 默认按照 uid 排序
        if (StringUtils.isBlank(sort)) {
            sort = "uid desc";
        }
        if (username != null) {
            username = username.trim();
        }
        if (email != null) {
            email = email.trim();
        }
        if (telphone != null) {
            telphone = telphone.trim();
        }
    }
}
