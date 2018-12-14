package com.sse.controller;

import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.student.StudentParam;
import com.sse.model.student.StudentResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @email
 * @date 2018-11-05 21:30
 */

@RestController
public class StudentController {

    @RequestMapping(value = "/student/1", method = RequestMethod.POST)
    public ResponseResultHolder<StudentResponse> getStudent(@RequestBody RequestParamHolder<StudentParam> stuParamHolder) {
        System.out.println(stuParamHolder);
        ResponseResultHolder<StudentResponse> result = new ResponseResultHolder<>();
        result.setResult(StudentResponse.builder().name("json").age(23).build());
        return result;
    }

    @RequestMapping(value = "/student/2", method = RequestMethod.POST)
    public ResponseResultHolder<StudentResponse> getFormStudent(@RequestParam("name") String name,
                                                                @RequestParam("id") Integer id,
                                                                @RequestParam("addr") String addr) {
        System.out.println("name:" + name);
        System.out.println("id:" + id);
        System.out.println("addr:" + addr);
        ResponseResultHolder<StudentResponse> result = new ResponseResultHolder<>();
        result.setResult(StudentResponse.builder().name("form").age(15).build());
        return result;
    }

    @RequestMapping(value = "/student/3", method = RequestMethod.GET)
    public ResponseResultHolder<StudentResponse> getStudentByGetMethod() {
        ResponseResultHolder<StudentResponse> result = new ResponseResultHolder<>();
        result.setResult(StudentResponse.builder().name("get method no args").age(26).build());
        return result;
    }
}
