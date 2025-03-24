package com.familywarehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {

    private Long id;

    @NotBlank(message = "Product id must not be null")
    private String productId;

    @NotBlank(message = "Product name must not be blank")
    private String productName;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 0, message = "Quantity must be greater or equal than 0")
    private Integer quantity;

    @NotNull(message = "MinimumQuantity must not be null")
    @Min(value = 0, message = "MinimumQuantity must be greater or equal than 0")
    private Integer minimumQuantity;

    private Boolean includeToShopList;
}
