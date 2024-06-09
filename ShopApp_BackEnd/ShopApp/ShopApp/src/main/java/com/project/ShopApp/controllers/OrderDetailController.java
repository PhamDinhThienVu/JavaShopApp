package com.project.ShopApp.controllers;


import com.project.ShopApp.dtos.OrderDetailDTO;
import com.project.ShopApp.models.OrderDetail;
import com.project.ShopApp.response.OrderDetailResponse;
import com.project.ShopApp.services.IOrderDetailService;
import com.project.ShopApp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_detail")
@AllArgsConstructor
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO newOrderDetailDTO
            )
    {
        try {
            OrderDetailResponse newOrderDetail = orderDetailService.createOrderDetail(newOrderDetailDTO);
            return ResponseEntity.ok("create order detail success full!" + newOrderDetailDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") long id)
    {
        try {
            OrderDetailResponse orderDetail = orderDetailService.getOrderDetail(id);
            return ResponseEntity.ok(orderDetail);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Get list orderDetails of a order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") long id)
    {
        try {
            List<OrderDetailResponse> orderDetails = orderDetailService.getOrderDetailsOfOrder(id);
            return ResponseEntity.ok(orderDetails);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") long id,
            @Valid @RequestBody OrderDetailDTO newOrderDetailDTO
    ){
        try {
            OrderDetailResponse orderDetailUpdated = orderDetailService.updateOrderDetail(id, newOrderDetailDTO);
            return ResponseEntity.ok(orderDetailUpdated);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @Valid @PathVariable("id") long id
    ){
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok("Deleted product with id = " + id + "successfull!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
