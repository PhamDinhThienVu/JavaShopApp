package com.project.ShopApp.services;

import com.project.ShopApp.dtos.OrderDTO;
import com.project.ShopApp.dtos.OrderDetailDTO;
import com.project.ShopApp.models.*;
import com.project.ShopApp.repositories.OrderDetailRepository;
import com.project.ShopApp.repositories.OrderRepository;
import com.project.ShopApp.repositories.ProductRepository;
import com.project.ShopApp.response.OrderDetailResponse;
import com.project.ShopApp.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDOT) throws Exception {
        //Check exist of Order
        Order existingOrder = orderRepository.findById(orderDetailDOT.getOrderId())
                .orElseThrow(() -> new Exception("Order not found!"));

        Product existingProduct = productRepository.findById(orderDetailDOT.getProductId())
                        .orElseThrow(() -> new Exception("Product not found!"));

        //Convert orderDTO => Order, test thu vien Model Maper
        //Skip set ID - database auto increment
        //Skip setOrder - not support
        //Skip setProduct - not support
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(OrderDetailDTO.class, OrderDetail.class);


        OrderDetail newOrderDetail = new OrderDetail();
        modelMapper.map(orderDetailDOT, newOrderDetail);
        newOrderDetail.setOrder(existingOrder);
        newOrderDetail.setProduct(existingProduct);
        orderDetailRepository.save(newOrderDetail);

        //Map from order to orderDetailResponse return to json
        modelMapper.typeMap(OrderDetail.class, OrderDetailResponse.class);
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        modelMapper.map(newOrderDetail, orderDetailResponse);
        return orderDetailResponse;
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) throws Exception {
        OrderDetail existingOrderDetauk = orderDetailRepository.findById(id)
                .orElseThrow(() -> new Exception("OrderDetail not found!"));

        //Map from order to orderDetailResponse return to json
        modelMapper.typeMap(OrderDetail.class, OrderDetailResponse.class);
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        modelMapper.map(existingOrderDetauk, orderDetailResponse);
        return orderDetailResponse;

    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO newOrderDetailDTO) throws Exception {
        //Check exist of Order
        OrderDetail existingOrderDetauk = orderDetailRepository.findById(id)
                .orElseThrow(() -> new Exception("Order Detail not found!"));

        Order existingOrder = orderRepository.findById(newOrderDetailDTO.getOrderId())
                .orElseThrow(() -> new Exception("Order not found!"));

        Product existingProduct = productRepository.findById(newOrderDetailDTO.getProductId())
                .orElseThrow(() -> new Exception("Product not found!"));



        existingOrderDetauk.setProduct(existingProduct);
        existingOrderDetauk.setOrder(existingOrder);
        existingOrderDetauk.setColor(newOrderDetailDTO.getColor());
        existingOrderDetauk.setPrice(newOrderDetailDTO.getPrice());
        existingOrderDetauk.setTotalMoney(newOrderDetailDTO.getTotalMoney());
        existingOrderDetauk.setNumberOfProducts(newOrderDetailDTO.getNumberOfProducts());

        orderDetailRepository.save(existingOrderDetauk);


        //Map from order to orderDetailResponse return to json
        modelMapper.typeMap(OrderDetail.class, OrderDetailResponse.class);
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        modelMapper.map(existingOrderDetauk, orderDetailResponse);
        return orderDetailResponse;

    }

    @Override
    public void deleteOrderDetail(Long id) throws Exception {
        OrderDetail existingOrderDetauk = orderDetailRepository.findById(id)
                .orElseThrow(() -> new Exception("Order Detail not found!"));
        orderDetailRepository.delete(existingOrderDetauk);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetailsOfOrder(Long orderID) {
        List<OrderDetail> orderDetailsList = orderDetailRepository.findByOrderId(orderID);

        //Map from orders to List<OrderResponse>
        List<OrderDetailResponse> ordersDetailResponses = new ArrayList<>();
        for(OrderDetail order: orderDetailsList){
            modelMapper.typeMap(OrderDetail.class, OrderDetailResponse.class);
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            modelMapper.map(order, orderDetailResponse);
            ordersDetailResponses.add(orderDetailResponse);
        }
        return ordersDetailResponses;

    }
}
