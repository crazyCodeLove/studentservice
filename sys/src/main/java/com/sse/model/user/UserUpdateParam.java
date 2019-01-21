package com.sse.model.user;

import com.sse.model.param.RequestParamBase;
import lombok.*;

import javax.validation.constraints.NotNull;
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
public class UserUpdateParam extends RequestParamBase {
    @NotNull(message = "uid 不能为 null")
    private Long uid;
    private String email;
    private String telphone;
    private Date birthday;

    @Override
    public void validParamInParam() {
        super.validParamInParam();
    }

}
