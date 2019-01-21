package com.sse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.AppStart;
import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.user.User;
import com.sse.model.user.UserDeleteParam;
import com.sse.model.user.UserSaveParam;
import com.sse.model.user.UserUpdateParam;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * @author pczhao
 * @email
 * @date 2019-01-21 14:03
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppStart.class) // 指定 springboot 启动类
public class UserControllerTest {
    private MockMvc mvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;// springboot 项目自动注入到环境中。具体位置：JacksonAutoConfiguration 类下的 jacksonObjectMapper 方法

    private User user = null;
    private Random random = new Random();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
    }

    @Test
    public void saveTest() throws Exception {
        UserSaveParam userSaveParam = UserSaveParam.builder()
                .username("save" + random.nextInt())
                .password("123")
                .email("nice12@123.com")
                .telphone("124578")
                .birthday(new Date())
                .build();
        RequestParamHolder<UserSaveParam> saveParam = RequestParamHolder.setRequestParam(userSaveParam);
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(saveParam);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("saveParam:" + saveParam + "\n");
        MvcResult mvcResult = mvc.perform(post("/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonData)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // json 参数和类型
                .andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
        System.out.println("responseContent:" + mvcResult.getResponse().getContentAsString() +"\n\n");
        ResponseResultHolder<User> resultHolder = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructParametricType(ResponseResultHolder.class, User.class));
        user = resultHolder.getResult();
        saveParamEqualsUser(userSaveParam, user);
    }

    private void saveParamEqualsUser(UserSaveParam param, User user) {
        Assert.assertEquals(param.getUsername(), user.getUsername());
        Assert.assertEquals(param.getEmail(), user.getEmail());
        Assert.assertEquals(param.getTelphone(), user.getTelphone());
        Assert.assertEquals(param.getBirthday().getTime(), user.getBirthday().getTime());

    }

    @Test
    public void updateTest() throws Exception {
        UserUpdateParam userUpdateParam = UserUpdateParam.builder()
                .uid(user.getUid())
                .email(String.valueOf(random.nextInt(10000000)) + "@163.com")
                .telphone(String.valueOf(random.nextInt()))
                .birthday(new Date())
                .build();
        RequestParamHolder<UserUpdateParam> updateParam = RequestParamHolder.setRequestParam(userUpdateParam);
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(updateParam);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("updateParam:" + updateParam + "\n");
        MvcResult mvcResult = mvc.perform(put("/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonData)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // json 参数和类型
                .andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
        System.out.println("responseContent:" + mvcResult.getResponse().getContentAsString() + "\n\n");
        ResponseResultHolder<User> resultHolder = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructParametricType(ResponseResultHolder.class, User.class));
        user = resultHolder.getResult();
        updateParamEqualsUser(userUpdateParam, user);
    }

    private void updateParamEqualsUser(UserUpdateParam param, User user) {
        Assert.assertEquals(param.getUid(), user.getUid());
        Assert.assertEquals(param.getEmail(), user.getEmail());
        Assert.assertEquals(param.getTelphone(), user.getTelphone());
        Assert.assertEquals(param.getBirthday().getTime(), user.getBirthday().getTime());

    }

    @Test
    public void deleteTest() throws Exception {
        List<Long> uids = new ArrayList<>();
        uids.add(user.getUid());
        UserDeleteParam userDeleteParam = UserDeleteParam.builder()
                .uids(uids)
                .build();

        RequestParamHolder<UserDeleteParam> deleteParam = RequestParamHolder.setRequestParam(userDeleteParam);
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(deleteParam);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("deleteParam:" + deleteParam + "\n");
        MvcResult mvcResult = mvc.perform(delete("/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonData)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // json 参数和类型
                .andReturn();
        Assert.assertEquals(mvcResult.getResponse().getStatus(), 200);
        System.out.println("responseContent:" + mvcResult.getResponse().getContentAsString() + "\n\n");
    }

    @Test
    public void testAll() throws Exception {
        saveTest();
        updateTest();
        deleteTest();

    }


}
