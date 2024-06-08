package com.project.ShopApp.services;

import com.project.ShopApp.response.ProductResponse;
import com.project.ShopApp.dtos.ProductDTO;
import com.project.ShopApp.dtos.ProductImageDTO;
import com.project.ShopApp.models.Product;
import com.project.ShopApp.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IProductService {
     Product createProduct(ProductDTO productDTO);

     Product getProductById(long id);

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO);


}
