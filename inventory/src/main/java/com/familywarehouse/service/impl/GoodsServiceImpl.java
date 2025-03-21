package com.familywarehouse.service.impl;

import com.familywarehouse.dto.GoodsDto;
import com.familywarehouse.entity.Goods;
import com.familywarehouse.exception.GoodsAlreadyExistsException;
import com.familywarehouse.exception.ResourceNotFoundException;
import com.familywarehouse.mapper.GoodsMapper;
import com.familywarehouse.repository.GoodsRepository;
import com.familywarehouse.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.familywarehouse.contants.GoodsConstants.GOODS;
import static com.familywarehouse.contants.GoodsConstants.ID;

@Service
@AllArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    @Override
    public Page<GoodsDto> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return goodsRepository.findAll(pageable).map(GoodsMapper::toDto);
    }

    @Override
    public Page<GoodsDto> findAllWithLowQuantity(Boolean includeToShopList, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return goodsRepository.findAllWhereLowQuantity(includeToShopList, pageable).map(GoodsMapper::toDto);

    }

    @Override
    public void createGoods(GoodsDto goodsDto) {
        Optional<Goods> existedGoods = goodsRepository.findByProductId(goodsDto.getProductId());
        if (existedGoods.isPresent()) {
            throw new GoodsAlreadyExistsException("Goods already exists with product id: " + goodsDto.getProductId());
        }
        goodsRepository.save(GoodsMapper.toEntity(goodsDto));
    }

    @Override
    public GoodsDto findById(Long id) {
        Goods goods = goodsRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(GOODS, ID, Long.toString(id)));
        return GoodsMapper.toDto(goods);
    }

    @Override
    public boolean updateGoods(GoodsDto goodsDto) {
        boolean isUpdated = false;
        if (goodsDto != null) {
            Long id = goodsDto.getId();
            Goods goods = goodsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(GOODS, ID, Long.toString(id)));
            goodsRepository.save(GoodsMapper.toEntity(goodsDto, goods));
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteGoods(Long id) {
        goodsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GOODS, ID, Long.toString(id)));
        goodsRepository.deleteById(id);
        return true;
    }
}
