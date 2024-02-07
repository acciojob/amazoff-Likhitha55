package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String,Order> orderDB;
    HashMap<String,DeliveryPartner> partnerDB;
    HashMap<String,List<String>> partnerOrdersPairDB;
    HashMap<String,String> orderPartnersPairDB;
    public OrderRepository(){
        this.orderDB = new HashMap<>();
        this.partnerDB = new HashMap<>();
        this.partnerOrdersPairDB = new HashMap<>();
        this.orderPartnersPairDB = new HashMap<>();
    }
    public void addOrder(Order order){
        int dT = order.getDeliveryTime();
        if(order.getId().length()!=0 && dT!=0 && !orderDB.containsKey(order.getId())){
            orderDB.put(order.getId(),order);
        }

    }
    public void addPartner(String id){
        if(id.length()!=0 && !partnerDB.containsKey(id)){
            partnerDB.put(id,new DeliveryPartner(id));
            partnerOrdersPairDB.put(id,new ArrayList<>());
        }

    }
    public List<String> getAllOrders(){
        List<String> list = new ArrayList<>();
        for(String s :orderDB.keySet()){
            list.add(s);
        }
        return list;
    }
    public void addOrderPartnerPair(String orderId,String partnerId){
        if(!orderDB.containsKey(orderId) || !partnerDB.containsKey(partnerId) || !partnerOrdersPairDB.containsKey(partnerId)) return ;
        partnerOrdersPairDB.get(partnerId).add(orderId);
        orderPartnersPairDB.put(orderId,partnerId);
        DeliveryPartner obj = partnerDB.get(partnerId);
        obj.setNumberOfOrders(obj.getNumberOfOrders()+1);
    }
    public Order getOrderById(String orderId){
        if(orderDB.containsKey(orderId)){
            return orderDB.get(orderId);
        }
        return null;

    }
    public DeliveryPartner getPartnerById(String partnerId){
        if(partnerDB.containsKey(partnerId)){
            return partnerDB.get(partnerId);
        }
        return null;
    }
    public int getOrderCountByPartnerId(String partnerId){
        if(partnerDB.containsKey(partnerId)){
            return partnerDB.get(partnerId).getNumberOfOrders();
        }
        return 0;
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        if(partnerOrdersPairDB.containsKey(partnerId)){
            return partnerOrdersPairDB.get(partnerId);
        }
        return null;
    }
    public int getCountOfUnassignedOrders(){
        return orderDB.size() - orderPartnersPairDB.size();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        return partnerOrdersPairDB.get(partnerId).size();
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        if(!partnerOrdersPairDB.containsKey(partnerId)) return null;
        List<String> orders = partnerOrdersPairDB.get(partnerId);
        int last = 0;
        for(String id : orders){
            int dT = orderDB.get(id).getDeliveryTime();
            if(dT>last){
                last=dT;
            }
        }
        int h = last/60;
        int m = last-(h*60);
        String HH = String.format("%02d", h);
        String MM = String.format("%02d", m);
        return (HH+":"+MM) ;
    }
    public String deletePartnerById(String partnerId){
        if(!partnerDB.containsKey(partnerId)) return "Failure";
        //Del partner from partnerDB
        partnerDB.remove(partnerId);

        //Del partner from partnerOrderPairsDB
        List<String> list = partnerOrdersPairDB.get(partnerId);
        partnerOrdersPairDB.remove(partnerId);

        //Make unassigned order in orderPartnerPairsDB
        for(String id : orderPartnersPairDB.keySet()){
            orderPartnersPairDB.remove(id);
        }
        return "Success";
    }
    public String deleteOrderById(String orderId){

        orderDB.remove(orderId);
        System.out.println(orderDB);
        String partnerId = null;
        for(String id : orderPartnersPairDB.keySet()){
            if(id.equals(orderId)){
                partnerId = orderPartnersPairDB.get(id);
            }
            break;
        }
        List<String> list = partnerOrdersPairDB.get(partnerId);
        for(String id:list){
            if(id.equals(orderId)){
                list.remove(id);
            }
            break;
        }
        orderPartnersPairDB.remove(orderId);
        partnerOrdersPairDB.put(partnerId,list);
        partnerDB.get(partnerId).setNumberOfOrders(list.size());
        return "Success";

    }
}
