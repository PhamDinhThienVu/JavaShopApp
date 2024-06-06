package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private int orderId;


    @Min(value = 1, message = "Product's ID is not equal 0")
    @JsonProperty("product_id")
    private int productId;

    @Min(value = 0, message = "Product's Price is not below 0")
    private Long price;


    @Min(value = 1, message = "Quantity of products must be >= 1")
    @JsonProperty("number_of_product")
    private int numberOfProducts;

    @Min(value = 0, message = "Total money must be >= 0")
    @JsonProperty("total_money")
    private int totalMoney;

    private  String color;
}
