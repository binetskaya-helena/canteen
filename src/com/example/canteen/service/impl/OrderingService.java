package com.example.canteen.service.impl;

import com.example.canteen.service.TimeService;
import com.example.canteen.service.data.Order;
import com.example.canteen.service.data.PublishingDetails;

/** Controls for when the ordering is enabled according to the menu schedule state. */
public class OrderingService {
    private final OrdersProcessor _ordersProcessor;
    private final MenuSchedule _menuSchedule;
    private final TimeService _timeService;

    public OrderingService(MenuSchedule menuSchedule, OrdersProcessor ordersProcessor, TimeService timeService) {
        _ordersProcessor = ordersProcessor;
        _menuSchedule = menuSchedule;
        _timeService = timeService;

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
        order.setDate(_timeService.now());
        return _ordersProcessor.submit(order);
    }
}
