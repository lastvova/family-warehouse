package com.familywarehouse.controller;

import com.familywarehouse.contants.GoodsConstants;
import com.familywarehouse.dto.ErrorResponseDto;
import com.familywarehouse.dto.GoodsDto;
import com.familywarehouse.dto.ResponseDto;
import com.familywarehouse.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
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
@RequestMapping(value = "/goods", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class GoodsController {

    private final GoodsService goodsService;

    @Operation(summary = "Create goods in Family-Warehouse", description = "REST API to create Goods")
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping
    public ResponseEntity<ResponseDto> createGoods(@Valid @RequestBody GoodsDto goodsDto) {
        goodsService.createGoods(goodsDto);
        return generateResponseEntity(HttpStatus.CREATED, GoodsConstants.MESSAGE_201);
    }

    @Operation(summary = "Fetch goods in Family-Warehouse", description = "REST API goods")
    @ApiResponse(responseCode = "201", description = "HTTP Status OK")
    @GetMapping
    public ResponseEntity<Page<GoodsDto>> fetchAllGoods(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                                        @RequestParam(required = false, defaultValue = "5") @Min(1) int size) {
        Page<GoodsDto> allGoods = goodsService.findAll(page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allGoods);

    }

    @Operation(summary = "Fetch goods in Family-Warehouse", description = "REST API to fetch Goods")
    @ApiResponse(responseCode = "201", description = "HTTP Status OK")
    @GetMapping("/{id}")
    public ResponseEntity<GoodsDto> fetchGoods(@PathVariable Long id) {
        GoodsDto goods = goodsService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(goods);

    }

    @Operation(summary = "Update goods in Family-Warehouse", description = "REST API to update Goods")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @ApiResponse(responseCode = "409", description = "Request failed or there are some conflicts")
    @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateGoods(@PathVariable Long id, @Valid @RequestBody GoodsDto goodsDto) {
        if (!id.equals(goodsDto.getId())) {
            return generateResponseEntity(HttpStatus.BAD_REQUEST, GoodsConstants.MESSAGE_INCONSISTENT_GOODS_ID);
        }
        boolean isUpdated = goodsService.updateGoods(goodsDto);
        if (isUpdated) {
            return generateResponseEntity(HttpStatus.OK, GoodsConstants.MESSAGE_200);
        }
        return generateResponseEntity(HttpStatus.CONFLICT, GoodsConstants.MESSAGE_409);
    }

    @Operation(summary = "Delete goods in Family-Warehouse", description = "REST API to delete Goods")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @ApiResponse(responseCode = "409", description = "Request failed or there are some conflicts")
    @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteGoods(@PathVariable Long id) {
        boolean isUpdated = goodsService.deleteGoods(id);
        if (isUpdated) {
            return generateResponseEntity(HttpStatus.OK, GoodsConstants.MESSAGE_200);
        }
        return generateResponseEntity(HttpStatus.CONFLICT, GoodsConstants.MESSAGE_409);
    }

    @Operation(summary = "Fetch residues of goods in Family-Warehouse", description = "REST API goods")
    @ApiResponse(responseCode = "201", description = "HTTP Status OK")
    @GetMapping("/residues")
    public ResponseEntity<Page<GoodsDto>> fetchResiduesOfGoods(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                                               @RequestParam(required = false, defaultValue = "5") @Min(1) int size,
                                                               @RequestParam(required = false) Boolean includeToShopList) {
        Page<GoodsDto> allGoods = goodsService.findAllWithLowQuantity(includeToShopList, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allGoods);

    }

    private ResponseEntity<ResponseDto> generateResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ResponseDto(Integer.toString(httpStatus.value()), message));
    }
}
