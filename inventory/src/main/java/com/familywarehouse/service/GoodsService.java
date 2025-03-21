package com.familywarehouse.service;

import com.familywarehouse.dto.GoodsDto;
import org.springframework.data.domain.Page;

public interface GoodsService {

    Page<GoodsDto> findAll(int pageNumber, int pageSize);

    Page<GoodsDto> findAllWithLowQuantity(Boolean includeToShopList, int pageNumber, int pageSize);

    void createGoods(GoodsDto goodsDto);

    GoodsDto findById(Long id);

    boolean updateGoods(GoodsDto goodsDto);

    boolean deleteGoods(Long id);
}
