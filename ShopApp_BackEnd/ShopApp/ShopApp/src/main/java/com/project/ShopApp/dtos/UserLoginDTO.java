package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO{
    @NotBlank(message = "Have to enter phone number!")
    @JsonProperty("phone_number")
    private  String phoneNumber;



    @NotBlank(message = "Please Enter password!")
    @JsonProperty("password")
    private String password;

}


