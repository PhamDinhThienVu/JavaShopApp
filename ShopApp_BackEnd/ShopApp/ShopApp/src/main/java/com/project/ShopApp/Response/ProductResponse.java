package com.project.ShopApp.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ShopApp.dtos.BaseResponse;
import com.project.ShopApp.models.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import  java.util.List;
import org.springframework.web.multipart.MultipartFile;


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
