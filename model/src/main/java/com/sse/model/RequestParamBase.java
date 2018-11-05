package com.sse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pczhao
 * @email
 * @date 2018-11-05 21:14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestParamBase {
    private Integer id;
    private String name;

}
