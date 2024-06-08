package com.project.ShopApp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Have to enter phone number!")
    @JsonProperty("phone_number")
    private  String phoneNumber;

    @JsonProperty("address")
    private String address;

    @NotBlank(message = "Please Enter password!")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "Please retype password!")
    @JsonProperty("retype_password")
    private String retypePassword;


    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @NotNull(message = "Can not recognized user role!")
    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("is_active")
    private boolean is_active;
}
;