package com.familywarehouse.mapper;

import com.familywarehouse.dto.GoodsDto;
import com.familywarehouse.entity.Goods;

public class GoodsMapper {

    private GoodsMapper() {
    }

    public static GoodsDto toDto(Goods goods) {
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setId(goods.getId());
        goodsDto.setProductName(goods.getProductName());
        goodsDto.setProductId(goods.getProductId());
        goodsDto.setQuantity(goods.getQuantity());
        goodsDto.setMinimumQuantity(goods.getMinimumQuantity());
        goodsDto.setIncludeToShopList(goods.getIncludeToShopList());
        return goodsDto;
    }

    public static Goods toEntity(GoodsDto goodsDto) {
        Goods goods = new Goods();
        return toEntity(goodsDto, goods);
    }

    public static Goods toEntity(GoodsDto goodsDto, Goods goods) {
        goods.setProductId(goodsDto.getProductId());
        goods.setProductName(goodsDto.getProductName());
        goods.setQuantity(goodsDto.getQuantity());
        goods.setMinimumQuantity(goodsDto.getMinimumQuantity());
        goods.setIncludeToShopList(goodsDto.getIncludeToShopList());
        return goods;
    }
}
