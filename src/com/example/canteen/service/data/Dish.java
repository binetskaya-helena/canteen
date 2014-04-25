package com.example.canteen.service.data;

import java.io.Serializable;
import java.math.BigDecimal;

/** Contains information about some dish cooked in the canteen. */
public class Dish implements Serializable {
    private final String _name;
    private final String _description;
    private final BigDecimal _price;

    public Dish(String name, String description, BigDecimal price) {
        _name = name;
        _description = description;
        _price = price;
    }

    /** Name of the dish. */
    public String name() {
        return _name;
    }

    /** The displayed description of the dish. */
    public String description() {
        return _description;
    }

    /** The price for one portion of the dish. */
    public BigDecimal price() {
        return _price;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o || ((Dish)o)._name.equals(this._name));
    }

    @Override
    public int hashCode() {
        return _name.hashCode();
    }
}
