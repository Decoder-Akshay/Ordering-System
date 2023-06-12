package com.order.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.order.model.Customer;
import com.order.model.Item;

@Component
public class DiscountCalculator {

	 private static final double DISCOUNT_TIER_1 = 0.1; // 10%
	    private static final double DISCOUNT_TIER_2 = 0.2; // 20%
	    private static final double BFCM_DISCOUNT = 0.15; // 15%

	    public double calculateDiscount(Customer customer, List<Item> items) {
	        int orderCount = customer.getOrders().size();
	        double totalDiscount = 0.0;

	        if (orderCount > 10 && orderCount <= 20) {
	            totalDiscount += DISCOUNT_TIER_1;
	        } else if (orderCount > 20) {
	            totalDiscount += DISCOUNT_TIER_2;
	        }

	        totalDiscount += BFCM_DISCOUNT;

	        return totalDiscount;
	    }
}
