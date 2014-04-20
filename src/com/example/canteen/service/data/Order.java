package com.example.canteen.service.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order implements Serializable {
    private User _user;
    private Map<Dish, Integer> _items = new LinkedHashMap<Dish, Integer>();
    private String _id;

    public void addItem(Dish dish, int quantity) {
        if (!_items.containsKey(dish)) {
            _items.put(dish, 0);
        }
        _items.put(dish, _items.get(dish) + quantity);
    }

    public Map<Dish, Integer> items() {
        return _items;
    }

    public void setUser(User user) {
        _user = user;
    }

    public User user() {
        return _user;
    }

    public void setID(String ID) {
        _id = ID;
    }

    public String ID() {
        return _id;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<Dish, Integer> item : _items.entrySet()) {
            total = total.add(item.getKey().price().multiply(new BigDecimal(item.getValue())));
        }
        return total;
    }
}