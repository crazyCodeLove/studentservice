package com.sse.adapter.mybatis.mapper;

import com.sse.annotation.MultiDataSource;
import com.sse.config.datasource.DataSourceConfig;
import com.sse.model.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-20 9:25
 */

@Repository
public interface UserMapper {

    void save(User user);

    void saveInLog1(User user);

    void saveInLog2(User user);

    void saveBatch(@Param("users") List<User> users);

    void update(User user);

    void delete(@Param("uids") List<Long> uids);

    User get(User user);

    User getInLog1(User user);

    User getInLog2(User user);

    User getByUid(@Param("uid") long uid);

    List<User> getList(User user);

    /**
     * 查询数据库中已存在的所有用户名
     *
     * @param usernames 待查询的用户名集合
     * @return 已存在的用户名集合
     */
    Set<String> getUsernameExistSet(@Param("usernames") Set<String> usernames);
}
