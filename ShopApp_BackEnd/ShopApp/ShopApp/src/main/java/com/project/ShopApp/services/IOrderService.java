package com.project.ShopApp.services;

import com.project.ShopApp.response.OrderResponse;
import com.project.ShopApp.dtos.OrderDTO;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;

    OrderResponse getOrder(Long id);

    OrderResponse updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<OrderResponse> getAllOrders(Long userId);


}
