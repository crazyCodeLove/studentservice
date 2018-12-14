package com.sse.model.student;

import com.sse.model.ParamBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @email
 * @date 2018-11-05 21:14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentParam extends ParamBase {

    @NotNull(message = "StudentParam.id 不能为null")
    private Integer id;

    @NotBlank(message = "StudentParam.name 不能为空")
    private String name;

    @NotEmpty(message = "StudentParam.fruits 不能为空")
    @Valid
    private List<Fruit> fruits;

}
