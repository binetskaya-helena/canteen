package com.example.canteen.service.impl;

import com.example.canteen.service.data.Dish;
import com.example.canteen.service.data.Order;

import java.util.*;

/** Stores all the submitted orders in the system. Provides access to the list of submitted orders. */
public class OrdersProcessor {
    private static int _lastId;

    private final List<Order> _orders = new LinkedList<Order>();
    private boolean _orderingEnabled = false;

    public class OrderingDisabledException extends RuntimeException {}
    public class EmptyOrderException extends RuntimeException {}

    /** Enables or disables the making of orders. */
    public void setOrderingEnabled(boolean orderingEnabled) {
        _orderingEnabled = orderingEnabled;
    }

    /** Creates a new order in the system. The ordering should be enabled for the order to be created, otherwise
     * an OrderingDisabledException is thrown. The order should contain order items, otherwise
     * an EmptyOrderException is thrown.
     */
    public Order submit(Order order) throws OrderingDisabledException, EmptyOrderException {
        if (!_orderingEnabled) throw new OrderingDisabledException();
        if (0 == order.items().size()) throw new EmptyOrderException();

        _lastId++;
        order.setID(String.format("%05X", _lastId));
        _orders.add(order);
        return order;
    }

    /** Removes the given order from the system. */
    public void cancel(Order order) {
        _orders.remove(order);
    }

    /** Returns the list of all dishes ordered for the given period of time. */
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
