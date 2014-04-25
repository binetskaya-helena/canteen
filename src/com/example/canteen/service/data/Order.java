package com.example.canteen.service.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/** Contains information about an order. */
public class Order implements Serializable {
    private User _user;
    private Map<Dish, Integer> _items = new LinkedHashMap<Dish, Integer>();
    private String _id;
    private Date _date;

    /** Adds the given dish in the list of ordered items. If the given dish already present in the list the given
     * quantity is just added to the current quantity.
     */
    public void addItem(Dish dish, int quantity) {
        if (!_items.containsKey(dish)) {
            _items.put(dish, 0);
        }
        _items.put(dish, _items.get(dish) + quantity);
    }

    /** Removes the given quantity of the given dish from this order. */
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

    /** The list of dishes in this order with their quantity. */
    public Map<Dish, Integer> items() {
        return _items;
    }

    public void setUser(User user) {
        _user = user;
    }

    /** The user who made this order. */
    public User user() {
        return _user;
    }

    public void setID(String ID) {
        _id = ID;
    }

    /** The internal system identifier of this order. */
    public String ID() {
        return _id;
    }

    public void setDate(Date date) {
        _date = date;
    }

    /** The date when this order was made. */
    public Date date() {
        return _date;
    }

    /** Calculates the total price for all items in this order. */
    public BigDecimal calculateTotalPrice() {
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<Dish, Integer> item : _items.entrySet()) {
            total = total.add(item.getKey().price().multiply(new BigDecimal(item.getValue())));
        }
        return total;
    }
}
