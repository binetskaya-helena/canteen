package com.example.canteen.service.data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/** Contains information about a menu provided in the canteen. */
public class Menu {
    private String _id;
    private final List<Dish> _dishes = new LinkedList<Dish>();
    private Date _publishingDate;
    private Date _orderingDeadline;
    private Date _validThrough;

    public void setId(String id) {
        _id = id;
    }

    /** The internal identifier of the menu in the system. */
    public String id() {
        return _id;
    }

    /** Adds the given dish in the list of dishes in the menu. */
    public void addDish(Dish dish) {
        _dishes.add(dish);
        // todo: remove duplicates
    }

    /** The list of dishes in this menu. */
    public List<Dish> dishes() {
        return _dishes;
    }

    public void setPublishingDate(Date date) {
        _publishingDate = date;
    }

    /** The date when this menu should be published in the system as the current menu. */
    public Date publishingDate() {
        return _publishingDate;
    }

    public void setOrderingDeadline(Date orderingDeadline) {
        _orderingDeadline = orderingDeadline;
    }

    /** The date when the system should stop receiving orders for dishes from this menu. */
    public Date orderingDeadline() {
        return _orderingDeadline;
    }

    public void setValidThrough(Date validThrough) {
        _validThrough = validThrough;
    }

    /** The date for when this menu is not current any more. */
    public Date validThrough() {
        return _validThrough;
    }
}
