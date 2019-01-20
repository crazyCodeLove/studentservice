package com.sse.adapter.mybatis.mapper;

import com.sse.model.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author ZHAOPENGCHENG
 * date  2019-01-20 9:25
 */

@Mapper
public interface UserMapper {
    void save(User user);

    void update(User user);

    void delete(List<Long> uids);

    User get(User user);

    List<User> getList(User user);
}
