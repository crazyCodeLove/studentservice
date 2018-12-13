package com.sse.model.student;

import com.sse.model.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @email
 * @date 2018-11-05 21:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse extends ResponseBase {
    private String name;
    private Integer age;
}
