package com.project.ShopApp.services;

import com.project.ShopApp.response.OrderResponse;
import com.project.ShopApp.dtos.OrderDTO;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;

    OrderResponse getOrder(Long id) throws Exception;

    OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws Exception;

    void deleteOrder(Long id) throws Exception;

    List<OrderResponse> getAllOrdersOfUser(Long userId);

    List<OrderResponse> getAllOrders();

}
