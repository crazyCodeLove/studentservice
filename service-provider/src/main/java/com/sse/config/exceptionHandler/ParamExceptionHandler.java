package com.sse.config.exceptionHandler;

import com.sse.exception.ParamRTException;
import com.sse.exception.RTBaseException;
import com.sse.model.ResponseBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ZHAOPENGCHENG
 * @email
 * @date 2018-12-13 20:57
 */

@RestControllerAdvice
@Slf4j
public class ParamExceptionHandler {

    /**
     * 参数校验错误，错误码统一为：1000，错误原因放到 message 中
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ParamRTException.class)
    public ResponseBase paramExceptionHandle(RTBaseException e) {
        log.error(e.getMessage());
        return ResponseBase.builder().error(new ResponseBase.ResponseError(1000, e.getMessage())).build();
    }

}
