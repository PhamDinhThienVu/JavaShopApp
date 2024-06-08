package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {

    @JsonProperty("product_id")
    private Long product_id;

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Name must be greater than 5 and less than 200")
    private String image_url;
}
