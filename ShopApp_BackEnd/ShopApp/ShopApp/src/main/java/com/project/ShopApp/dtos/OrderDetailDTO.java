package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ShopApp.models.Order;
import com.project.ShopApp.models.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    @Min(value = 1, message = "Order's ID is not equal 0")
    @JsonProperty("order_id")
    private Long orderId;


    @Min(value = 1, message = "Product's ID is not equal 0")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value = 0, message = "Product's Price is not below 0")
    @JsonProperty("price")
    private Long price;


    @Min(value = 1, message = "Quantity of products must be >= 1")
    @JsonProperty("number_of_product")
    private int numberOfProducts;

    @Min(value = 0, message = "Total money must be >= 0")
    @JsonProperty("total_money")
    private int totalMoney;

    @JsonProperty("color")
    private  String color;

}
