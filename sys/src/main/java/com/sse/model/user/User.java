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
@ToString
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

    /**
     * 使用 mask 中非 null 字段填充 des 中的对应字段
     * @param des 待修改数据
     * @param mask 修改数据
     */
    public static void changeWithNonNull(User des, User mask) {
        if (des == null || mask == null) {
            return;
        }
        if (mask.getUid() != null) {
            des.setUid(mask.getUid());
        }
        if (mask.getUsername() != null) {
            des.setUsername(mask.getUsername());
        }
        if (mask.getPassword() != null) {
            des.setPassword(mask.getPassword());
        }
        if (mask.getEmail() != null) {
            des.setEmail(mask.getEmail());
        }
        if (mask.getTelphone() != null) {
            des.setTelphone(mask.getTelphone());
        }
        if (mask.getBirthday() != null) {
            des.setBirthday(mask.getBirthday());
        }
        if (mask.getUpdateTime() != null) {
            des.setUpdateTime(mask.getUpdateTime());
        }
    }
}
