package com.familywarehouse.repository;

import com.familywarehouse.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Page<Goods> findAll(Pageable pageable);

    Optional<Goods> findByProductId(String productId);

    @Query("SELECT g FROM Goods g WHERE g.quantity <= g.minimumQuantity AND (:includeToShopList IS NULL OR g.includeToShopList = :includeToShopList)")
    Page<Goods> findAllWhereLowQuantity(@Param("includeToShopList") Boolean includeToShopList, Pageable pageable);
}
