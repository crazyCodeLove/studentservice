package com.sse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @email
 * @date 2018-11-05 21:14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestParamBase implements Serializable {

    @NotNull(message = "id 不能为空")
    private Integer id;

    @NotBlank(message = "name 不能为空")
    private String name;

    @NotEmpty(message = "fruits 不能为空")
    @Valid
    private List<Fruit> fruits;

}
