package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order){
        UUID uuid = UUID.randomUUID();
        order.setId(uuid.toString());
        orderRepository.addOrder(order);
    }
    public void addPartner(String id){
        orderRepository.addPartner(id);
    }
    public List<String> getAllOrders(){
        return orderRepository.getAllOrders();
    }
    public void addOrderPartnerPair(String orderId,String partnerId){
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }
    public Order getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepository.getPartnerById(partnerId);
    }
    public int getOrderCountByPartnerId(String partnerId){
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }
    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }
    public String deletePartnerById(String partnerId){
        return orderRepository.deletePartnerById(partnerId);
    }
    public String deleteOrderById(String orderId){
        return orderRepository.deleteOrderById(orderId);
    }
}
