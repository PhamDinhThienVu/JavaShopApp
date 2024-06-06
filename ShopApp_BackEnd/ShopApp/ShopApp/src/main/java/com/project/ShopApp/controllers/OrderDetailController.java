package com.project.ShopApp.controllers;


import com.project.ShopApp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_detail")
public class OrderDetailController {


    @PostMapping
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO newOrderDetailDTO
            )
    {
        return ResponseEntity.ok("create order detail success full!" + newOrderDetailDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") long id)
    {
        return ResponseEntity.ok("get order detail success full!" + id);
    }


    //Get list orderDetails of a order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") long id)
    {
        return ResponseEntity.ok("get order details success full! " + id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") long id,
            @Valid @RequestBody OrderDetailDTO newOrderDetailDTO
    ){
        return ResponseEntity.ok("update order detail success full!" + newOrderDetailDTO + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @Valid @PathVariable("id") long id
    ){
        return ResponseEntity.ok("delete order detail success full!" + id);
    }

}
