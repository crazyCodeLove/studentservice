package com.sse.controller;

import com.sse.model.ResponseBase;
import com.sse.model.student.StudentParam;
import com.sse.model.student.StudentResponse;
import com.sse.util.ValidateUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @email
 * @date 2018-11-05 21:30
 */

@RestController
public class StudentController {

    @RequestMapping(value = "/student/1", method = RequestMethod.POST)
    public ResponseBase getStudent(@RequestBody StudentParam studentParam) {
        ValidateUtil.validate(studentParam);
        System.out.println(studentParam);
        StudentResponse result = new StudentResponse();
        result.setName("json");
        result.setAge(23);
        return result;
    }

    @RequestMapping(value = "/student/2", method = RequestMethod.POST)
    public ResponseBase getFormStudent(@RequestParam("name") String name,
                                       @RequestParam("id") Integer id,
                                       @RequestParam("addr") String addr) {
        System.out.println("name:" + name);
        System.out.println("id:" + id);
        System.out.println("addr:" + addr);
        StudentResponse result = new StudentResponse();
        result.setName("form");
        result.setAge(23);
        return result;
    }
}
