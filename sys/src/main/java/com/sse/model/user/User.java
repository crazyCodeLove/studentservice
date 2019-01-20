package com.sse.model.user;

import cn.hutool.crypto.SecureUtil;
import com.sse.config.SecureConfig;
import lombok.*;

import java.util.Date;

/**
 * author pczhao
 * date 2019-01-17 9:28
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long uid;
    private String username;
    private String password;
    private String email;
    private String telphone;
    private Date birthday;
    private Date createTime;
    private Date updateTime;

    public static User encrypt(User user) {
        if (user == null) {
            return null;
        }
        if (user.getPassword() != null) {
            user.setPassword(SecureUtil.sha256("" + user.getPassword() + user.getUsername()));
        }
        return user;
    }
}
