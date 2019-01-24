package com.sse.model.user;

import com.sse.model.param.RequestParamBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author pczhao <br/>
 * date 2019-01-21 20:31
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordParam extends RequestParamBase {
    @NotNull(message = "uid 不能为空")
    private Long uid;
    @NotBlank(message = "password 不能为空")
    private String password;

    @Override
    public void validParamInParam() {
        super.validParamInParam();
    }

    @Override
    public void preHandle() {
        super.preHandle();
        password = password.trim();
    }
}
