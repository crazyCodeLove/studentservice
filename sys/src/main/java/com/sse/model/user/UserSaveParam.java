package com.sse.model.user;

import com.sse.model.param.RequestParamBase;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-20 12:29
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveParam extends RequestParamBase {
    @NotBlank(message = "username 为空")
    private String username;
    @NotBlank(message = "password 为空")
    private String password;
    private String email;
    private String telphone;
    private Date birthday;

    @Override
    public void validParamInParam() {
    }

    @Override
    public void preHandle() {
        username = username.trim();
        password = password.trim();
        if (email != null) {
            email = email.trim();
        }
        if (telphone != null) {
            telphone = telphone.trim();
        }
    }
}
