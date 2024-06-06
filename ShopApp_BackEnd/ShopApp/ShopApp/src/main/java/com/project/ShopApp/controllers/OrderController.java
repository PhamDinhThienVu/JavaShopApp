package com.project.ShopApp.controllers;


import com.project.ShopApp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {


    @PostMapping("")
    public ResponseEntity<String> addOrder(
            @Valid
            @RequestBody
            OrderDTO orderDTO,
            BindingResult result) {
        try {

            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages.toString());
            }
            return ResponseEntity.ok("Create order successfully!" + orderDTO.toString());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    /*
    * Get order of a user by user_id
    *
    * */
    @GetMapping("/{user_id}")
    public ResponseEntity<?> gerOrders(
            @Valid @PathVariable("user_id") long userId
            ) {

        try{
            return ResponseEntity.ok("Get the orders of a user successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /*
    * Update an order
    * */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO
    ){
        return ResponseEntity.ok("Update order successfully!");
    }


    /*
    * Delete an order
    * */
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteOrder(
            @Valid @PathVariable long id
    ){
        //Update "active" attribute to false

        return ResponseEntity.ok("Delete order successfully!");
    }

}
