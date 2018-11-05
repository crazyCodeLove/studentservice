package com.sse.api;

import com.sse.model.RequestParamBase;
import com.sse.model.Response;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author pczhao
 * @email
 * @date 2018-11-05 21:24
 */

@FeignClient(value = "service-provider")
public interface StudentServiceApi {

    @RequestMapping(value = "/student/1", method = RequestMethod.POST)
    public Response getStudent(@RequestBody RequestParamBase param);

}
