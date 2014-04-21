package com.example.canteen.service.data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Menu {
    private String _id;
    private final List<Dish> _dishes = new LinkedList<Dish>();
    private Date _publishingDate;
    private Date _orderingDeadline;
    private Date _validThrough;

    public void setId(String id) {
        _id = id;
    }

    public String id() {
        return _id;
    }

    public void addDish(Dish dish) {
        _dishes.add(dish);
        // todo: remove duplicates
    }

    public List<Dish> dishes() {
        return _dishes;
    }

    public void setPublishingDate(Date date) {
        _publishingDate = date;
    }

    public Date publishingDate() {
        return _publishingDate;
    }

    public void setOrderingDeadline(Date orderingDeadline) {
        _orderingDeadline = orderingDeadline;
    }

    public Date orderingDeadline() {
        return _orderingDeadline;
    }

    public void setValidThrough(Date validThrough) {
        _validThrough = validThrough;
    }

    public Date validThrough() {
        return _validThrough;
    }
}
