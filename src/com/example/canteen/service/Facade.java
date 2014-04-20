package com.example.canteen.service;

import com.example.canteen.service.data.AuthToken;
import com.example.canteen.service.data.Menu;
import com.example.canteen.service.data.Order;
import com.example.canteen.service.data.User;

public interface Facade {
    class NotAuthorizedException extends RuntimeException {}
    class DomainError extends RuntimeException {}

    AuthToken authenticate(String name, String password);
    void registerUser(String name, String password) throws DomainError;
    User getUser(AuthToken authToken);

    Menu getCurrentMenu(AuthToken authToken);
    Order submitOrder(Order order, AuthToken authToken);
}
