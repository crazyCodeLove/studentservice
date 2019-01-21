package com.sse.controller;

import com.sse.config.exception.ParamNullException;
import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.user.*;
import com.sse.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 队请求数据进行预处理、加密处理
 * author ZHAOPENGCHENG
 * date  2019-01-20 12:27
 */

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseResultHolder<User> save(@RequestBody RequestParamHolder<UserSaveParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("请求参数为空");
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

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseResultHolder<User> update(@RequestBody RequestParamHolder<UserUpdateParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("请求参数为空");
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
            throw new ParamNullException("请求参数为空");
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
            throw new ParamNullException("请求参数为空");
        }
        userService.delete(param.getParam().getUids());
        return ResponseResultHolder.ok();
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ResponseResultHolder<List<User>> userList(@RequestBody RequestParamHolder<UserListParam> param) {
        if (param.getParam() == null) {
            throw new ParamNullException("请求参数为空");
        }
        UserListParam ListParam = param.getParam();
        User user = User.builder()
                .uid(ListParam.getUid())
                .username(ListParam.getUsername())
                .password(ListParam.getPassword())
                .email(ListParam.getEmail())
                .telphone(ListParam.getTelphone())
                .birthday(ListParam.getBirthday())
                .build();
        return ResponseResultHolder.setResult(userService.getList(User.encrypt(user)));
    }


}
