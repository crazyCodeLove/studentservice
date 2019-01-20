package com.sse.model.user;

import com.sse.model.param.RequestParamBase;
import lombok.*;

import java.util.Date;

/**
 * author ZHAOPENGCHENG
 * date  2019-01-20 12:39
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListParam extends RequestParamBase {
    private Long uid;
    private String username;
    private String password;
    private String email;
    private String telphone;
    private Date birthday;
    private Date createTime;
    private Date updateTime;

    @Override
    public void validParamInParam() {
        super.validParamInParam();
    }
}
