package com.project.ShopApp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse{
    private Long id;


    @JsonProperty("user_id")
    private  Long userId;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private  String email;


    @JsonProperty("phone_number")
    private String phoneNumber;


    @JsonProperty("address")
    private String address;

    @JsonProperty("note")
    private String note;


    @JsonProperty("total_money")
    private double totalMoney;


    @JsonProperty("order_date")
    private LocalDate orderDate;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;



    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("payment_date")
    private Date paymentDate;


    @JsonProperty("status")
    private String status;

    @JsonProperty("is_active")
    private boolean is_active;
}
