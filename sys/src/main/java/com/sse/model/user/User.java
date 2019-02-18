package com.sse.model.user;

import cn.hutool.crypto.SecureUtil;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 对于用户信息中，不能返回给客户端密码。
 * author pczhao <br/>
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
    private String username; // 用户名是唯一的，不可重复。创建后不可更改
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
        if (user.getPassword() != null && user.getUsername() != null) {
            user.setPassword(SecureUtil.sha256("" + user.getPassword() + user.getUsername()));
        } else {
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 清空用户的密码
     *
     * @param user 待清空密码的用户对象
     */
    public static User removePassword(User user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 使用 mask 中非 null 字段填充 des 中的对应字段
     *
     * @param des  待修改数据
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

    private static final String USER_REDIS_PREFIX = "user_";

    /**
     * 根据用户 uid 生成在 redis 中缓存的 key
     * @param user 含有 uid 的 用户信息
     */
    public static String getUserRedisKey(User user) {
        if (user != null && user.getUid() != null) {
            return USER_REDIS_PREFIX + user.getUid();
        }
        return "";
    }

    /**
     * 根据用户 uid 生成在 redis 中缓存的 key
     * @param uid 用户在数据库中的 uid
     */
    public static String getUserRedisKey(long uid) {
        return USER_REDIS_PREFIX + uid;
    }

    /**
     * 根据 uid 列表生成 redis 缓存中的 key
     * @param uids 待缓存的 uid 列表
     */
    public static List<String> getUserRedisKeyList(List<Long> uids) {
        List<String> klist = new ArrayList<>();
        for (Long k : uids) {
            klist.add(USER_REDIS_PREFIX + k);
        }
        return klist;
    }
}
