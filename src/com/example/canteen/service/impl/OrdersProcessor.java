package com.example.canteen.service.impl;

import com.example.canteen.service.data.Order;

public class OrdersProcessor {
    public static int _lastId;

    public Order submit(Order order) {
        _lastId++;
        order.setID(String.format("%05X", _lastId));
        return order;
    }
}
