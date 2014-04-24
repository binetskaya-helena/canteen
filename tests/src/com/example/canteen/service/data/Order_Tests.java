package com.example.canteen.service.data;

import android.test.AndroidTestCase;

import java.math.BigDecimal;

public class Order_Tests extends AndroidTestCase {
    public void testAggregatedDishesAdding() {
        Order order = new Order();
        Dish dish = new Dish("test dish", "good test dish", new BigDecimal(5.45));

        order.addItem(dish, 42);
        assert order.items().size() == 1;

        order.addItem(dish, 1989);
        assert order.items().get(dish) == 2031;
    }

    public void testAggregatedDishesRemoval() {
        Order order = new Order();
        Dish dish = new Dish("test dish", "good test dish", new BigDecimal(5.45));

        order.addItem(dish, 1989);
        assert order.items().size() == 1;

        order.removeItem(dish, 42);
        assert order.items().size() == 1;
        assert order.items().get(dish) == 1947;

        order.removeItem(dish, 3000);
        assert order.items().size() == 0;
    }
}
