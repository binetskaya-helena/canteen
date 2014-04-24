package com.example.canteen.service.impl;

import com.example.canteen.service.data.Dish;
import com.example.canteen.service.data.Order;

import java.util.*;

public class OrdersProcessor {
    private static int _lastId;

    private final List<Order> _orders = new LinkedList<Order>();
    private boolean _orderingEnabled = false;

    public class OrderingDisabledException extends RuntimeException {}
    public class EmptyOrderException extends RuntimeException {}

    public void setOrderingEnabled(boolean orderingEnabled) {
        _orderingEnabled = orderingEnabled;
    }

    public Order submit(Order order) throws OrderingDisabledException {
        if (!_orderingEnabled) throw new OrderingDisabledException();
        if (0 == order.items().size()) throw new EmptyOrderException();

        _lastId++;
        order.setID(String.format("%05X", _lastId));
        _orders.add(order);
        return order;
    }

    public void cancel(Order order) {
        _orders.remove(order);
    }

    public Map<Dish, Integer> getAggregatedDishes(Date from, Date to) {
        Map<Dish, Integer> dishes = new LinkedHashMap<Dish, Integer>();
        for (Order order : _orders) {
            if ((from == null || order.date().after(from)) && (to == null || order.date().before(to))) {
                for (Map.Entry<Dish, Integer> entry : order.items().entrySet()) {
                    Dish dish = entry.getKey();
                    if (!dishes.containsKey(dish)) {
                        dishes.put(dish, 0);
                    }
                    dishes.put(dish, dishes.get(dish) + entry.getValue());
                }
            }
        }
        return dishes;
    }
}
