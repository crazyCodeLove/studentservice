package com.sse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pczhao
 * @email
 * @date 2018-11-05 21:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private String name;
    private Integer age;
}
