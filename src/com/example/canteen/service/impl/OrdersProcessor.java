package com.example.canteen.service.impl;

import com.example.canteen.service.data.Dish;
import com.example.canteen.service.data.Order;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrdersProcessor {
    private static int _lastId;

    private final List<Order> _orders = new LinkedList<Order>();
    private boolean _orderingEnabled = false;

    public class OrderingDisabledException extends RuntimeException {}

    public void setOrderingEnabled(boolean orderingEnabled) {
        _orderingEnabled = orderingEnabled;
    }

    public Order submit(Order order) throws OrderingDisabledException {
        if (_orderingEnabled) {
            _lastId++;
            order.setID(String.format("%05X", _lastId));
            _orders.add(order);
            return order;

        } else {
            throw new OrderingDisabledException();
        }
    }

    public void cancel(Order order) {
        _orders.remove(order);
    }

    public Map<Dish, Integer> getAggregatedDishes() {
        Map<Dish, Integer> dishes = new LinkedHashMap<Dish, Integer>();
        for (Order order : _orders) {
            for (Map.Entry<Dish, Integer> entry : order.items().entrySet()) {
                if (!dishes.containsKey(entry.getKey())) {
                    dishes.put(entry.getKey(), 0);
                }
                dishes.put(entry.getKey(), dishes.get(entry.getKey()) + entry.getValue());
            }
        }
        return dishes;
    }
}
