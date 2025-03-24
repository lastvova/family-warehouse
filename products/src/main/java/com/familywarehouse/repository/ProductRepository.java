package com.familywarehouse.repository;

import com.familywarehouse.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(String category, Pageable pageable);

    Optional<Product> findByArticleNumber(String articleNumber);

}
