package com.sse.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * <p>
 * json 工具类，将对象转换成 json 字符串；将对象的 json 字符串转换成指定类型
 * </p>
 * author pczhao<br/>
 * date  2019-08-28 13:41
 */

@Slf4j
public class JsonObjectUtil {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将 json 字符串转换成指定类型对象，目标类型是 Class<T>
     *
     * @param jsonContent json 字符串
     * @param clazz       目标类型
     */
    public static <T> T convertJsonToType(String jsonContent, Class<T> clazz) {
        if (StringUtils.isNotBlank(jsonContent) && clazz != null) {
            try {
                return objectMapper.readValue(jsonContent, clazz);
            } catch (IOException e) {
                log.error("convert [" + jsonContent + "] to [" + clazz.getTypeName() + "] failed.", e);
            }
        }
        return null;
    }

    /**
     * 将 json 字符串转换成指定类型对象，目标类型是 TypeReference<T>
     *
     * @param jsonContent json 字符串
     * @param type        目标类型
     */
    public static <T> T convertJsonToType(String jsonContent, TypeReference<T> type) {
        if (StringUtils.isNotBlank(jsonContent) && type != null) {
            try {
                return objectMapper.readValue(jsonContent, type);
            } catch (IOException e) {
                log.error("convert [" + jsonContent + "] to [" + type.getType().getTypeName() + "] failed.", e);
            }
        }
        return null;
    }

    /**
     * 将对象转换成 json 串
     *
     * @param object 待转对象
     * @return json 字符串
     */
    public static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("convert [" + object.toString() + "] to json failed.", e);
        }
        return "";
    }

}
