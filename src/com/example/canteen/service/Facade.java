package com.example.canteen.service;

import com.example.canteen.service.data.*;

import java.util.Date;
import java.util.Map;

public interface Facade {
    class NotAuthorizedException extends RuntimeException {}
    class DomainError extends RuntimeException {}

    AuthToken authenticate(String name, String password);

    static String REGISTER_USER_ACTION = "POST user";
    void registerUser(String name, String password) throws DomainError;

    static String GET_USER_ACTION = "GET user";
    User getUser(String name, AuthToken authToken);

    static String GET_CURRENT_MENU_ACTION = "GET menu/published";
    PublishingDetails getCurrentMenu(AuthToken authToken);

    static String SUBMIT_ORDER_ACTION = "POST order";
    Order submitOrder(Order order, AuthToken authToken);

    static String CREATE_MENU_ACTION = "POST menu";
    Menu scheduleMenu(Menu menu, AuthToken authToken);

    static String REMOVE_MENU_ACTION = "DELETE menu";
    void cancelMenu(Menu menu, AuthToken authToken);

    static String GET_ORDERED_DISHES_ACTION = "GET orders/aggregated";
    Map<Dish, Integer> getOrderedDishes(Date from, Date to, AuthToken authToken);
}
