package com.sse.controller;

import com.sse.model.RequestParamBase;
import com.sse.model.Response;
import com.sse.util.ValidateUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @email
 * @date 2018-11-05 21:30
 */

@RestController
public class StudentController {

    @RequestMapping(value = "/student/1", method = RequestMethod.POST)
    public Response getStudent(@RequestBody RequestParamBase paramBase) {
        ValidateUtil.validate(paramBase);
        System.out.println(paramBase);
        Response result = Response.builder().age(23).name("json").build();
        return result;
    }

    @RequestMapping(value = "/student/2", method = RequestMethod.POST)
    public Response getFormStudent(@RequestParam("name") String name,
                                   @RequestParam("id") Integer id,
                                   @RequestParam("addr") String addr) {
        System.out.println("name:" + name);
        System.out.println("id:" + id);
        System.out.println("addr:" + addr);
        Response result = Response.builder().age(23).name("form").build();
        return result;
    }
}
