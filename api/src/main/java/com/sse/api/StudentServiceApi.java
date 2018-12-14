package com.sse.api;

import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.student.StudentParam;
import com.sse.model.student.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author pczhao
 * @email
 * @date 2018-11-05 21:24
 */

@FeignClient(value = "student-service-provider")
public interface StudentServiceApi {

    @RequestMapping(value = "/student-service-provider/student/1", method = RequestMethod.POST)
    ResponseResultHolder<StudentResponse> getStudent(@RequestBody RequestParamHolder<StudentParam> stuParamHolder);

}
