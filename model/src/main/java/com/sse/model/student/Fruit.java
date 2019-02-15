package com.sse.model.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * date 2018-11-15 14:16
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Fruit {

    @NotBlank(message = "Fruit.color can not be blank")
    private String color;

    @NotNull(message = "Fruit.price can not be null")
    private Integer price;
}
