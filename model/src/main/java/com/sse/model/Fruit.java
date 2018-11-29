package com.sse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @email
 * @date 2018-11-15 14:16
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Fruit {
    @NotBlank(message = "color 不能为空")
    private String color;
    @NotNull(message = "price 不能为空")
    private Integer price;
}
