package com.example.canteen.service.impl;

import com.example.canteen.service.data.Order;
import com.example.canteen.service.data.PublishingDetails;

public class OrderingService {
    private final OrdersProcessor _ordersProcessor;
    private final MenuSchedule _menuSchedule;

    public OrderingService(MenuSchedule menuSchedule, OrdersProcessor ordersProcessor) {
        _ordersProcessor = ordersProcessor;
        _menuSchedule = menuSchedule;

        _menuSchedule.setOnMenuUpdate(new Runnable() {
            @Override
            public void run() {
                checkOrderingAvailability();
            }
        });
        checkOrderingAvailability();
    }

    private void checkOrderingAvailability() {
        PublishingDetails details = _menuSchedule.currentMenu();
        _ordersProcessor.setOrderingEnabled(details.orderingEnabled);
    }

    public Order submitOrder(Order order) {
        return _ordersProcessor.submit(order);
    }
}
