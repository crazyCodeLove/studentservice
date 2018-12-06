package com.sse.controller;

import com.sse.model.RequestParamBase;
import com.sse.model.Response;
import com.sse.util.ValidateUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @email
 * @date 2018-11-05 21:30
 */

@RestController
public class StudentController {

    @RequestMapping(value = "/student/1", method = RequestMethod.POST)
    public Response getStudent(@RequestBody @Valid RequestParamBase paramBase) {
        ValidateUtil.validate(paramBase);
        System.out.println(paramBase);
        Response result = Response.builder().age(23).name("well").build();
        return result;
    }
}
