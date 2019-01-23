package com.sse.service.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sse.adapter.mybatis.mapper.UserMapper;
import com.sse.exception.user.UserExistException;
import com.sse.exception.user.UserNotExistException;
import com.sse.model.user.User;
import com.sse.model.user.UserListParam;
import com.sse.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 对外提供的一致性是根据 uid 来处理数据库中数据
 * </p>
 * author ZHAOPENGCHENG <br/>
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
     * 不会修改密码
     *
     * @param user 待修改数据，除了密码
     * @return 修改后的数据
     */
    @Transactional
    public User update(User user) {
        Date now = new Date();
        user.setUpdateTime(now);
        User src;
        if ((src = userMapper.get(User.builder().uid(user.getUid()).build())) == null) {
            // 用户不存在
            user.setUid(null);
            userMapper.save(user);
            return userMapper.get(user);
        }
        userMapper.update(user);
        User.changeWithNonNull(src, user);
        return src;
    }

    /**
     * 修改密码
     *
     * @param user 待修改密码的用户数据：uid 和 password
     */
    @Transactional
    public void changePassword(User user) {
        Date now = new Date();
        user.setUpdateTime(now);
        User src = userMapper.get(User.builder().uid(user.getUid()).build());
        if (src == null) {
            // 用户不存在
            throw new UserNotExistException("用户不存在。uid:" + user.getUid());
        }
        user.setUsername(src.getUsername());
        userMapper.update(User.encrypt(user));
    }


    @Transactional
    public void delete(List<Long> uids) {
        userMapper.delete(uids);
    }

    public User get(User user) {
        return userMapper.get(user);
    }

    /**
     * 相应数据中有两部分组成："page" 对应的值表示分页信息，"list" 对应的值表示当前页数据
     *
     * @param listParam 分页请求信息
     */
    public Map<String, Object> getList(UserListParam listParam) {
        Map<String, Object> result;
        User user = User.builder()
                .uid(listParam.getUid())
                .username(listParam.getUsername())
                .email(listParam.getEmail())
                .telphone(listParam.getTelphone())
                .birthday(listParam.getBirthday())
                .build();

        try {
            PageHelper.startPage(listParam.getCurrentPage(), listParam.getPageSize(), listParam.getSort());
            Page<User> r = (Page<User>) userMapper.getList(user);
            result = PageUtil.toResultMap(r);
        } finally {
            PageHelper.clearPage();
        }
        return result;
    }
}
