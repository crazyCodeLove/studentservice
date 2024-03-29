package com.sse.controller;

import com.sse.config.exception.ParamNullException;
import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.user.*;
import com.sse.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 队请求数据进行预处理、加密处理
 * </p>
 * author ZHAOPENGCHENG <br/>
 * date  2019-01-20 12:27
 */

@RestController
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseResultHolder<User> save(@RequestBody RequestParamHolder<UserSaveParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("request param is null");
        }
        UserSaveParam saveParam = param.getParam();
        User user = User.builder()
                .username(saveParam.getUsername())
                .password(saveParam.getPassword())
                .email(saveParam.getEmail())
                .telphone(saveParam.getTelphone())
                .birthday(saveParam.getBirthday())
                .build();
        return ResponseResultHolder.setResult(userService.save(User.encrypt(user)));
    }

    @RequestMapping(value = "/user/test", method = RequestMethod.POST)
    public ResponseResultHolder<User> savetest(@RequestBody RequestParamHolder<UserSaveParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("request param is null");
        }
        System.out.println(param.getParam());
        return ResponseResultHolder.ok();
    }

    @RequestMapping(value = "/user/batch", method = RequestMethod.POST)
    public ResponseResultHolder<Map<String, Object>> saveBatch(@RequestBody RequestParamHolder<List<UserSaveParam>> param) {
        if (param.getParam() == null || param.getParam().isEmpty()) {
            throw new ParamNullException("request param is null");
        }
        List<UserSaveParam> listSaveParam = param.getParam();
        List<User> users = new ArrayList<>(listSaveParam.size() * 2);
        for (UserSaveParam saveParam : listSaveParam) {
            User u = User.builder()
                    .username(saveParam.getUsername())
                    .password(saveParam.getPassword())
                    .email(saveParam.getEmail())
                    .telphone(saveParam.getTelphone())
                    .birthday(saveParam.getBirthday())
                    .build();
            users.add(User.encrypt(u));
        }
        return ResponseResultHolder.setResult(userService.saveBatch(users));
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseResultHolder<User> update(@RequestBody RequestParamHolder<UserUpdateParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("request param is null");
        }
        UserUpdateParam updateParam = param.getParam();
        User user = User.builder()
                .uid(updateParam.getUid())
                .email(updateParam.getEmail())
                .telphone(updateParam.getTelphone())
                .birthday(updateParam.getBirthday())
                .build();
        return ResponseResultHolder.setResult(userService.update(user));
    }

    @RequestMapping(value = "/user/password", method = RequestMethod.PUT)
    public ResponseResultHolder changePassword(@RequestBody RequestParamHolder<UserChangePasswordParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("request param is null");
        }
        UserChangePasswordParam updateParam = param.getParam();
        User user = User.builder()
                .uid(updateParam.getUid())
                .password(updateParam.getPassword())
                .build();
        userService.changePassword(user);
        return ResponseResultHolder.ok();
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseResultHolder delete(@RequestBody RequestParamHolder<UserDeleteParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("request param is null");
        }
        userService.delete(param.getParam().getUids());
        return ResponseResultHolder.ok();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseResultHolder<User> getUser(@RequestBody RequestParamHolder<UserGetParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("request param is null");
        }
        UserGetParam getParam = param.getParam();
        return ResponseResultHolder.setResult(userService.getByUid(getParam.getUid()));
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ResponseResultHolder<Map<String, Object>> userList(@RequestBody RequestParamHolder<UserListParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("request param is null");
        }
        UserListParam listParam = param.getParam();
        return ResponseResultHolder.setResult(userService.getList(listParam));
    }


}
