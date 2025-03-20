package com.familywarehouse.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Article number is required")
    private String articleNumber;

    @NotBlank(message = "Category is required")
    private String category;

    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    private List<String> tags;
}
