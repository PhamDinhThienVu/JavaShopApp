package com.project.ShopApp.response;

import com.project.ShopApp.models.Product;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private String name;
    private float price;
    private String thumbnail;
    private String description;
    private Long categoryId;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse response = ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .categoryId(product.getCategory().getId())
                .build();
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdateAt());
        return response;
    }
}
