package com.project.ShopApp.services;

import com.project.ShopApp.dtos.OrderDTO;
import com.project.ShopApp.models.*;
import com.project.ShopApp.response.OrderResponse;
import com.project.ShopApp.repositories.OrderRepository;
import com.project.ShopApp.repositories.UserRepository;
import com.project.ShopApp.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        //Check exist of user
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        //Convert orderDTO => Order, test thu vien Model Maper
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order newOrder = new Order();
        modelMapper.map(orderDTO, newOrder);

        newOrder.setUser(user);
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setStatus(OrderStatus.PENDING);

        //Check shippind date
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate() ;
        if(shippingDate.isBefore(LocalDate.now())){
            throw new Exception("Shipping Date must be at least to day!");
        }
        newOrder.setShippingDate(shippingDate);
        newOrder.set_active(true);
        orderRepository.save(newOrder);


        //Map from order to orderResponse return to json
        modelMapper.typeMap(Order.class, OrderResponse.class);
        OrderResponse orderResponse = new OrderResponse();
        modelMapper.map(newOrder, orderResponse);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrder(Long id) throws Exception {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new Exception("Order not found"));

        //Map from order to orderResponse return to json
        modelMapper.typeMap(Order.class, OrderResponse.class);
        OrderResponse orderResponse = new OrderResponse();
        modelMapper.map(existingOrder, orderResponse);
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        //Map from orders to List<OrderResponse>
        List<OrderResponse> ordersResponses = new ArrayList<>();
        for(Order order: orders){
            modelMapper.typeMap(Order.class, OrderResponse.class);
            OrderResponse orderResponse = new OrderResponse();
            modelMapper.map(order, orderResponse);
            ordersResponses.add(orderResponse);
        }
        return ordersResponses;
    }

    @Override
    public List<OrderResponse> getAllOrdersOfUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        //Map from orders to List<OrderResponse>
        List<OrderResponse> ordersResponses = new ArrayList<>();
        for(Order order: orders){
            modelMapper.typeMap(Order.class, OrderResponse.class);
            OrderResponse orderResponse = new OrderResponse();
            modelMapper.map(order, orderResponse);
            ordersResponses.add(orderResponse);
        }
        return ordersResponses;
    }




    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        //Check exist of user
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        //Check exist of oders
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Order not found"));


        //Convert orderDTO => Order
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        modelMapper.map(orderDTO, existingOrder);
        existingOrder.setUser(user);
        existingOrder.setOrderDate(LocalDate.now());
        existingOrder.setStatus(OrderStatus.PENDING);
        //Check shippind date
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate() ;
        if(shippingDate.isBefore(LocalDate.now())){
            throw new Exception("Shipping Date must be at least to day!");
        }
        existingOrder.setShippingDate(orderDTO.getShippingDate());
        existingOrder.set_active(orderDTO.is_active());
        orderRepository.save(existingOrder);


        //Map from order to orderResponse return to json
        modelMapper.typeMap(Order.class, OrderResponse.class);
        OrderResponse orderResponse = new OrderResponse();
        modelMapper.map(existingOrder, orderResponse);
        return orderResponse;
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        //Check exist of oders
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Order not found"));

        //Soft delete order
        if(existingOrder != null){
            existingOrder.set_active(false);
            orderRepository.save(existingOrder);
        }

    }



}
