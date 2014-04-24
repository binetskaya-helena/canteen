package com.example.canteen.service.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order implements Serializable {
    private User _user;
    private Map<Dish, Integer> _items = new LinkedHashMap<Dish, Integer>();
    private String _id;
    private Date _date;

    public void addItem(Dish dish, int quantity) {
        if (!_items.containsKey(dish)) {
            _items.put(dish, 0);
        }
        _items.put(dish, _items.get(dish) + quantity);
    }

    public void removeItem(Dish dish, int quantityToRemove) {
        if (_items.containsKey(dish)) {
            int orderedQuantity = _items.get(dish);
            orderedQuantity = (orderedQuantity > quantityToRemove ? orderedQuantity - quantityToRemove : 0);
            if (orderedQuantity > 0) {
                _items.put(dish, orderedQuantity);
            } else {
                _items.remove(dish);
            }
        }
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

    public void setDate(Date date) {
        _date = date;
    }

    public Date date() {
        return _date;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<Dish, Integer> item : _items.entrySet()) {
            total = total.add(item.getKey().price().multiply(new BigDecimal(item.getValue())));
        }
        return total;
    }
}
