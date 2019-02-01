package com.sse.service.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sse.adapter.mybatis.mapper.UserMapper;
import com.sse.exception.user.UserExistException;
import com.sse.exception.user.UserNotExistException;
import com.sse.model.user.User;
import com.sse.model.user.UserListParam;
import com.sse.service.redis.IRedisService;
import com.sse.service.redis.RedisService;
import com.sse.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.sse.constant.ResponseConstant.Batch.FAILED;

/**
 * <p>
 * 对外提供的一致性是根据 uid 来处理数据库中数据
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-20 9:24
 */

@Service
public class UserService implements IUserService {

    private UserMapper userMapper;
    private IRedisService<User> userRedisService;

    @Autowired
    public UserService(UserMapper userMapper, RedisService<User> userRedisService) {
        this.userMapper = userMapper;
        this.userRedisService = userRedisService;
    }

    /**
     * 添加用户数据
     *
     * @param user 用户数据
     * @return 数据库中添加的用户数据
     */
    @Transactional
    public User save(User user) {
        if (userMapper.get(User.builder().username(user.getUsername()).build()) != null) {
            throw new UserExistException(user.getUsername() + " 已存在");
        }
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        userMapper.save(user);
        return User.removePassword(userMapper.get(user));
    }

    /**
     * 批量保存用户数据。用户名已存在的到失败列表；重复的第一个添加成功。
     *
     * @param users 待添加用户数据
     * @return 插入结果。失败列表和成功列表
     */
    @Transactional
    public Map<String, Object> saveBatch(List<User> users) {
        Map<String, Object> result = new HashMap<>(2);
        List<User> successUsers = new ArrayList<>(users.size());
        List<User> failedUsers = new ArrayList<>(users.size());
        result.put(FAILED, failedUsers);
        Set<String> unames = new HashSet<>();
        for (User u : users) {
            unames.add(u.getUsername());
        }
        unames = userMapper.getUsernameExistSet(unames);
        Date now;//创建和更新时间
        for (User u : users) {
            if (!unames.contains(u.getUsername())) {
                now = new Date();
                unames.add(u.getUsername());
                u.setCreateTime(now);
                u.setUpdateTime(now);
                successUsers.add(u);
            } else {
                failedUsers.add(User.removePassword(u));
            }
        }
        userMapper.saveBatch(successUsers);
        return result;
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
        userRedisService.delete(User.getUserRedisKey(src));
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
        userRedisService.delete(User.getUserRedisKeyList(uids));
    }

    public User get(User user) {
        User u;
        if ((u = userRedisService.get(User.getUserRedisKey(user))) != null) {
            return u;
        }
        u = userMapper.get(user);
        if (u == null) {
            throw new UserNotExistException("用户不存在。uid:" + user.getUid());
        }
        userRedisService.set(User.getUserRedisKey(u), u);
        return u;
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
