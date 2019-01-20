package com.sse.service.user;

import com.sse.adapter.mybatis.mapper.UserMapper;
import com.sse.exception.user.UserExistException;
import com.sse.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 对外提供的一致性是根据 uid 来处理数据库中数据
 * author ZHAOPENGCHENG
 * date  2019-01-20 9:24
 */

@Service
public class UserService {

    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional
    public User save(User user) {
        if (userMapper.get(User.builder().username(user.getUsername()).password(user.getPassword()).build()) != null) {
            throw new UserExistException(user.getUsername() + " 已存在");
        }
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        userMapper.save(user);
        return userMapper.get(user);
    }

    /**
     * 更新用户数据，不存在则添加
     *
     * @param user
     * @return
     */
    @Transactional
    public User update(User user) {
        Date now = new Date();
        user.setUpdateTime(now);
        if (userMapper.get(User.builder().uid(user.getUid()).build()) == null) {
            // 用户不存在
            user.setUid(null);
            userMapper.save(user);
            return userMapper.get(user);
        }
        userMapper.update(user);
        return user;
    }

    @Transactional
    public void delete(List<Long> uids) {
        userMapper.delete(uids);
    }

    public User get(User user) {
        return userMapper.get(user);
    }

    public List<User> getList(User user) {
        return userMapper.getList(user);
    }
}
