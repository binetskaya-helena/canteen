package com.example.canteen.service.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class Dish implements Serializable {
    private final String _name;
    private final String _description;
    private final BigDecimal _price;

    public Dish(String name, String description, BigDecimal price) {
        _name = name;
        _description = description;
        _price = price;
    }

    public String name() {
        return _name;
    }

    public String description() {
        return _description;
    }

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
