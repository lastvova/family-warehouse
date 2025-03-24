package com.familywarehouse.service;

import com.familywarehouse.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductDto> getAllProducts(int pageNumber, int pageSize);

    ProductDto getProductById(String id);

    void createProduct(ProductDto productDto);

    boolean updateProduct(ProductDto productDto);

    boolean deleteProduct(String id);

}
