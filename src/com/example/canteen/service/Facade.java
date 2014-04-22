package com.example.canteen.service;

import com.example.canteen.service.data.*;

public interface Facade {
    class NotAuthorizedException extends RuntimeException {}
    class DomainError extends RuntimeException {}

    AuthToken authenticate(String name, String password);
    void registerUser(String name, String password) throws DomainError;
    User getUser(String name, AuthToken authToken);

    PublishingDetails getCurrentMenu(AuthToken authToken);
    Order submitOrder(Order order, AuthToken authToken);
}
