package com.sse.service.user;

import com.sse.model.user.User;
import com.sse.model.user.UserListParam;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-01-23 15:43
 */

public interface IUserService {

    User save(User user);

    Map<String, Object> saveBatch(List<User> users);

    User update(User user);

    void changePassword(User user);

    void delete(List<Long> uids);

    User get(User user);

    /**
     * 根据 uid 获取 User 对象
     */
    User getByUid(long uid);

    Map<String, Object> getList(UserListParam listParam);
}
