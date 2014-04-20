package com.example.canteen.service.data;

import java.util.Date;
import java.util.List;

public class Menu {
    private final List<Dish> _dishes;
    private Date _orderingDeadline;

    public Menu(List<Dish> dishes, Date orderingDeadline) {
        _dishes = dishes;
        _orderingDeadline = orderingDeadline;
    }

    public List<Dish> dishes() {
        return _dishes;
    }

    public Date orderingDeadline() {
        return _orderingDeadline;
    }
}
