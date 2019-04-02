package com.sse.util;

import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.ParamRTException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * author pczhao <br/>
 * date 2018-12-06 15:13
 */

public class ValidateUtil {

    /**
     * 使用hibernate注解校验参数
     */
    private static Validator validator = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory()
            .getValidator();

    /**
     * 注解校验参数
     *
     * @param obj 待校验参数
     */
    public static <T> void validate(T obj) {
        if (obj == null) {
            throw new ParamRTException(ExceptionCodeEnum.PARAM_NULL_EXCEPTION, ExceptionCodeEnum.PARAM_NULL_EXCEPTION.getNote());
        }
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        if (constraintViolations.size() > 0) {
            throw new ParamRTException(constraintViolations.iterator().next().getMessage());
        }
    }
}
