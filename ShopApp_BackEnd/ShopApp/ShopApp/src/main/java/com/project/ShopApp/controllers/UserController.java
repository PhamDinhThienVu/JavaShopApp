package com.project.ShopApp.controllers;


import com.project.ShopApp.dtos.UserDTO;
import com.project.ShopApp.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {


    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(
            @Valid
            @RequestBody
            UserDTO userDTO,
            BindingResult result
    ) {
        try {
            //Get all error messages and return break
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            //Check password and retype_password
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Passwords do not match");
            }

            return ResponseEntity.ok().body("Register successful");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody
            UserLoginDTO userLoginDTO
            ){

        //Check match

        //Return session
        return ResponseEntity.ok().body("Got token!");


    }
}
