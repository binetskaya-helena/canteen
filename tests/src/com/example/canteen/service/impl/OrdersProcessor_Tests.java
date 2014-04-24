package com.example.canteen.service.impl;

import android.test.AndroidTestCase;
import com.example.canteen.service.data.Dish;
import com.example.canteen.service.data.Order;

import java.math.BigDecimal;
import java.util.Map;

public class OrdersProcessor_Tests extends AndroidTestCase {
    public void testErrorOnDisabledOrdering() {
        OrdersProcessor processor = new OrdersProcessor();
        processor.setOrderingEnabled(false);

        Order order = new Order();
        order.addItem(new Dish("test dish 1", "good test dish 1", new BigDecimal(5.45)), 1);
        try {
            processor.submit(order);
            fail("Ordering should be disabled");
        } catch (OrdersProcessor.OrderingDisabledException ex) {
            // OK
        }
    }

    public void testFailingOnEmptyOrder() {
        OrdersProcessor processor = new OrdersProcessor();
        processor.setOrderingEnabled(true);
        Order emptyOrder = new Order();
        try {
            processor.submit(emptyOrder);
            fail("Should not submit an empty order");
        } catch (OrdersProcessor.EmptyOrderException ex) {
            // OK
        }
    }

    public void testAggregatedOrderedDishes() {
        OrdersProcessor processor = new OrdersProcessor();
        processor.setOrderingEnabled(true);

        Dish dish1 = new Dish("test dish 1", "good test dish 1", new BigDecimal(5.45));
        Dish dish2 = new Dish("test dish 2", "good test dish 2", new BigDecimal(7.62));

        Order order1 = new Order();
        order1.addItem(dish1, 42);
        order1.addItem(dish2, 1);
        processor.submit(order1);

        Order order2 = new Order();
        order2.addItem(dish1, 1989);
        processor.submit(order2);

        Map<Dish, Integer> dishes = processor.getAggregatedDishes(null, null);
        assert dishes.size() == 2;
        assert dishes.get(dish1) == 2031;
        assert dishes.get(dish2) == 1;
    }
}
