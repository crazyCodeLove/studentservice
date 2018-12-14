package com.sse.config.exceptionHandler;

import com.sse.exception.ParamRTException;
import com.sse.exception.RTExceptionBase;
import com.sse.model.ResponseResultHolder;
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
    public ResponseResultHolder paramExceptionHandle(RTExceptionBase e) {
        log.error(e.getMessage());
        return ResponseResultHolder.builder().error(new ResponseResultHolder.ResponseError(1000, e.getMessage())).build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResultHolder RuntimeExceptHandler(RuntimeException e) {
        e.printStackTrace();
        return ResponseResultHolder.builder().error(new ResponseResultHolder.ResponseError(500, "server internal error, engineers are rushing to repair ...")).build();
    }

}
