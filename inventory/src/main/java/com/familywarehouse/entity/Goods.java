package com.familywarehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goods")
public class Goods extends BaseEntity {

    @Column(name = "product_id", nullable = false, unique = true, updatable = false)
    private String productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Min(0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Min(0)
    @Column(name = "minimum_quantity", nullable = false)
    private Integer minimumQuantity;

    @Column(name = "include_to_shop_list")
    private Boolean includeToShopList;
}
