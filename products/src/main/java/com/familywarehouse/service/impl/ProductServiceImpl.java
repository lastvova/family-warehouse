package com.familywarehouse.service.impl;

import com.familywarehouse.dto.ProductDto;
import com.familywarehouse.entity.Product;
import com.familywarehouse.exception.ProductAlreadyExistedException;
import com.familywarehouse.exception.ResourceNotFoundException;
import com.familywarehouse.mapper.ProductMapper;
import com.familywarehouse.repository.ProductRepository;
import com.familywarehouse.service.ProductService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT = "Product";
    private static final String ID = "id";
    private final ProductRepository productRepository;


    @Override
    public Page<ProductDto> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable)
                .map(ProductMapper::toDto);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, id));
        return ProductMapper.toDto(product);
    }

    @Override
    public void createProduct(ProductDto productDto) {
        String articleNumber = productDto.getArticleNumber();
        Optional<Product> existedProduct = productRepository.findByArticleNumber(articleNumber);
        if (existedProduct.isPresent()) {
            throw new ProductAlreadyExistedException("Product already existed with given articleNumber: " + articleNumber);
        }
        productRepository.save(ProductMapper.toEntity(productDto));
    }

    @Override
    public boolean updateProduct(ProductDto productDto) {
        boolean isUpdated = false;
        if (productDto != null) {
            String id = productDto.getId();
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, id));
            checkIfArticleNumberAlreadyExistedInOtherDocument(productDto, productDto.getArticleNumber());
            productRepository.save(ProductMapper.toEntity(productDto, product));
            isUpdated = true;
        }
        return isUpdated;
    }

    private void checkIfArticleNumberAlreadyExistedInOtherDocument(ProductDto productDto, String articleNumberToCheck) {
        Optional<Product> existedProduct = productRepository.findByArticleNumber(articleNumberToCheck);
        if (existedProduct.isPresent() && !StringUtils.equals(existedProduct.get().getId(), productDto.getId())) {
            throw new ProductAlreadyExistedException("Product already existed with given articleNumber: " + articleNumberToCheck);
        }
    }

    @Override
    public boolean deleteProduct(String id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, id));
        productRepository.deleteById(id);
        return true;
    }
}
