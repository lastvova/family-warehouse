package com.familywarehouse.mapper;

import com.familywarehouse.dto.ProductDto;
import com.familywarehouse.entity.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setArticleNumber(product.getArticleNumber());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setTags(product.getTags());
        return dto;
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        return toEntity(productDto, product);
    }

    public static Product toEntity(ProductDto productDto, Product product) {
        product.setArticleNumber(productDto.getArticleNumber());
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setTags(productDto.getTags());
        return product;
    }
}
