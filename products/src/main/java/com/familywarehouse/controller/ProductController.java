package com.familywarehouse.controller;

import com.familywarehouse.constants.ProductConstants;
import com.familywarehouse.dto.ErrorResponseDto;
import com.familywarehouse.dto.ProductDto;
import com.familywarehouse.dto.ResponseDto;
import com.familywarehouse.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create a product in Family-Warehouse",
            description = "REST API to create product"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping
    public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
        return generateResponseEntity(HttpStatus.CREATED, ProductConstants.MESSAGE_201);
    }

    @Operation(
            summary = "Fetch a product in Family-Warehouse",
            description = "REST API to fetch product"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> fetchProduct(@PathVariable String id) {
        ProductDto productDto = productService.getProductById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productDto);
    }

    @Operation(
            summary = "Fetch all products in Family-Warehouse",
            description = "REST API to fetch product"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status OK"
    )
    @GetMapping
    public ResponseEntity<Page<ProductDto>> fetchAllProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false, defaultValue = "5") int size) {
        Page<ProductDto> allProducts = productService.getAllProducts(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allProducts);
    }

    @Operation(
            summary = "Update a product in Family-Warehouse",
            description = "REST API to update product"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Request failed or there are some conflicts"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable String id, @Valid @RequestBody ProductDto productDto) {
        if (!StringUtils.equals(id, productDto.getId())) {
            return generateResponseEntity(HttpStatus.BAD_REQUEST, ProductConstants.MESSAGE_INCONSISTENT_PRODUCT_ID);
        }
        boolean isUpdated = productService.updateProduct(productDto);
        if (isUpdated) {
            return generateResponseEntity(HttpStatus.OK, ProductConstants.MESSAGE_200);
        } else {
            return generateResponseEntity(HttpStatus.CONFLICT, ProductConstants.MESSAGE_409);
        }
    }

    @Operation(
            summary = "Delete a product in Family-Warehouse",
            description = "REST API to delete product"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Request failed or there are some conflicts"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable String id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return generateResponseEntity(HttpStatus.OK, ProductConstants.MESSAGE_200);
        } else {
            return generateResponseEntity(HttpStatus.CONFLICT, ProductConstants.MESSAGE_409);
        }
    }

    private ResponseEntity<ResponseDto> generateResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ResponseDto(Integer.toString(httpStatus.value()), message));
    }
}
