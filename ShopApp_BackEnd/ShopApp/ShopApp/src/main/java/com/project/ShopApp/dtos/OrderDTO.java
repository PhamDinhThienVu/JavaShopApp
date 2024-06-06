package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @Min(value = 1, message = "User is not equal 0")
    @JsonProperty("user_id")
    private  Long userId;


    @JsonProperty("fullname")
    private String fullName;


    @JsonProperty("email")
    private  String email;

    @NotBlank(message = "Please provide your phone number!")
    @JsonProperty("phone_number")
    private String phoneNumber;


    @JsonProperty("address")
    private String address;

    @JsonProperty("note")
    private String note;

    @Min(value = 0, message = "Total money must be >= 0")
    @JsonProperty("total_money")
    private double totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;

}
