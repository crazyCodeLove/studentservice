package com.sse.config.exception.handler;

import com.sse.exception.ParamRTException;
import com.sse.exception.RTExceptionBase;
import com.sse.model.ResponseResultHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常和运行时异常处理
 *
 * @author ZHAOPENGCHENG
 * @email
 * @date 2018-12-13 20:57
 */

@RestControllerAdvice
@Slf4j
public class RTExceptionHandler {



    /**
     * 参数校验错误，错误码统一为：1000，错误原因放到 message 中
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ParamRTException.class)
    public ResponseResultHolder paramExceptionHandle(RTExceptionBase e) {
        log.error(e.getMessage());
        return ResponseResultHolder.error(1000, e.getMessage());
    }

    /**
     * 运行时异常，打印错误信息，并返回500
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResultHolder RuntimeExceptHandler(RuntimeException e) {
        e.printStackTrace();
        return ResponseResultHolder.error(500, "server internal error, engineers are rushing to repair ...");
    }

}