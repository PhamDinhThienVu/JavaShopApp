package com.project.ShopApp.controllers;


import com.project.ShopApp.dtos.OrderDTO;
import com.project.ShopApp.models.Order;
import com.project.ShopApp.response.OrderResponse;
import com.project.ShopApp.services.IOrderService;
import com.project.ShopApp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> addOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result) {
        try {

            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages.toString());
            }

            OrderResponse orderResponse =  orderService.createOrder(orderDTO);

            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /*
     * Get order of a user by user_id
     *
     * */

    @GetMapping("/{id}")
    public ResponseEntity<?> gerOrder(
            @Valid @PathVariable("id") long orderId
    ) {

        try{
            OrderResponse orderResponse = orderService.getOrder(orderId);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    /*
    * Get order of a user by user_id
    *
    * */
    @GetMapping("")
    public ResponseEntity<?> getOrders() {
        try{
            List<OrderResponse> orderResponses = orderService.getAllOrders();
            return ResponseEntity.ok(orderResponses);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> gerOrdersOfUser(
            @Valid @PathVariable("user_id") long userId
    ) {
        try{
            List<OrderResponse> orderResponses = orderService.getAllOrdersOfUser(userId);
            return ResponseEntity.ok(orderResponses);
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
        try{
            OrderResponse orderResponse = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    /*
    * Delete an order
    * */
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteOrder(
            @Valid @PathVariable long id
    ){
        //Update "active" attribute to false
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Delete order successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

}
