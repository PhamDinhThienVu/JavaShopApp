package com.project.ShopApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.ShopApp.models.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);
}
