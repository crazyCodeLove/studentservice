package com.sse.model.user;

import com.sse.model.param.RequestParamBase;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-02-01 10:05
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetParam extends RequestParamBase {
    @NotNull(message = "uid 不能为 null")
    private Long uid;

    @Override
    public void validParamInParam() {
        super.validParamInParam();
    }

    @Override
    public void preHandle() {
        super.preHandle();
    }
}
