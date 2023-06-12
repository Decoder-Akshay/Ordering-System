package com.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.exceptions.OrderNotFoundException;
import com.order.model.Customer;
import com.order.model.Item;
import com.order.model.Order;
import com.order.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final DiscountCalculator discountCalculator;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerService customerService,
                        ItemService itemService, DiscountCalculator discountCalculator) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.itemService = itemService;
        this.discountCalculator = discountCalculator;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    public Order createOrder(Order order) {
        Customer customer = customerService.getCustomerById(order.getCustomer().getId());
        List<Item> items = itemService.getAllItemsByIds(order.getItemIds());
        double discount = discountCalculator.calculateDiscount(customer, items);
        order.setDiscount(discount);
        order.setItems(items);
        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setCustomer(order.getCustomer());
        existingOrder.setItems(itemService.getAllItemsByIds(order.getItemIds()));
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }


    
    
    
}
