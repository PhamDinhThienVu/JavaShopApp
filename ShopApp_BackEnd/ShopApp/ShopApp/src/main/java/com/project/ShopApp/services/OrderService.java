package com.project.ShopApp.services;

import com.project.ShopApp.dtos.OrderDTO;
import com.project.ShopApp.models.Order;
import com.project.ShopApp.models.OrderStatus;
import com.project.ShopApp.response.OrderResponse;
import com.project.ShopApp.repositories.OrderRepository;
import com.project.ShopApp.repositories.UserRepository;
import com.project.ShopApp.models.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<OrderResponse> getAllOrders(Long userId) {
        return List.of();
    }
}
