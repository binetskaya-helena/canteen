package com.example.canteen.service.data;

import java.util.Date;
import java.util.List;

public class Menu {
    private final List<Dish> _dishes;
    private Date _publishingDate;
    private Date _orderingDeadline;
    private Date _validThrough;

    public Menu(List<Dish> dishes, Date orderingDeadline) {
        _dishes = dishes;
        _orderingDeadline = orderingDeadline;
    }

    public List<Dish> dishes() {
        return _dishes;
    }

    public Date publishingDate() {
        return _publishingDate;
    }

    public Date orderingDeadline() {
        return _orderingDeadline;
    }

    public Date validThrough() {
        return _validThrough;
    }
}
