package com.sse.model.user;

import com.sse.model.param.RequestParamBase;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
public class UserDeleteParam extends RequestParamBase {
    @NotEmpty(message = "uid 列表不能为空")
    List<Long> uids;

    @Override
    public void validParamInParam() {
        super.validParamInParam();
    }
}
