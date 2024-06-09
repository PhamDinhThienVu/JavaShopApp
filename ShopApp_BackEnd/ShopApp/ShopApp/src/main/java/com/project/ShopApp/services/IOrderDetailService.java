package com.project.ShopApp.services;

import com.project.ShopApp.dtos.OrderDetailDTO;
import com.project.ShopApp.response.OrderDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project.ShopApp.models.OrderDetail;

import java.util.List;


public interface IOrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDOT) throws Exception;

    OrderDetailResponse getOrderDetail(Long id) throws Exception;

    OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData) throws Exception;

    void deleteOrderDetail(Long id) throws Exception;

    List<OrderDetailResponse> getOrderDetailsOfOrder(Long orderID);
}
