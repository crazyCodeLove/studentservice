package com.sse.config.exception.handler;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;
import com.sse.model.ResponseResultHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常和运行时异常处理
 * <p>
 * author ZHAOPENGCHENG <br/>
 * date 2018-12-13 20:57
 */

@RestControllerAdvice
@Slf4j
public class RTExceptionHandler {


    /**
     * 自定义异常统一处理
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = RTException.class)
    public ResponseResultHolder paramExceptionHandle(RTException e) {
        log.error(e.getMessage(), e);
        return ResponseResultHolder.error(e.getCode(), e.getMessage());
    }

    /**
     * 运行时异常，打印错误信息，并返回 500 状态码
     *
     * @param e 运行时异常
     * @return 异常结果
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResultHolder RuntimeExceptHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseResultHolder.error(ExceptionCodeEnum.RUNTIME_EXCEPTION);
    }

}
