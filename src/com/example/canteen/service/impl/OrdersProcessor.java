package com.example.canteen.service.impl;

import com.example.canteen.service.data.Order;

import java.util.LinkedList;
import java.util.List;

public class OrdersProcessor {
    private static int _lastId;

    private final List<Order> _orders = new LinkedList<Order>();
    private boolean _orderingEnabled = false;

    public class OrderingDisabledException extends RuntimeException {}

    public void setOrderingEnabled(boolean orderingEnabled) {
        _orderingEnabled = true;
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
}
