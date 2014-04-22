package com.example.canteen.client.api;

import com.example.canteen.service.Facade;
import com.example.canteen.service.data.AuthToken;
import com.example.canteen.service.data.Order;
import com.example.canteen.service.data.PublishingDetails;
import com.example.canteen.service.data.User;

public class Client {
    private final Facade _server;
    private AuthToken _authToken;
    private User _user;

    public Client(Facade server) {
        _server = server;
    }

    public void signIn(String name, String password) throws Facade.NotAuthorizedException {
        _authToken = _server.authenticate(name, password);
        _user = _server.getUser(name, _authToken);
    }

    public void signUp(String name, String password) throws Facade.DomainError {
        _server.registerUser(name, password);
        try {
            signIn(name, password);

        } catch (Facade.NotAuthorizedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public PublishingDetails getCurrentMenu() {
        return _server.getCurrentMenu(_authToken);
    }

    public Order submitOrder(Order order) throws Facade.NotAuthorizedException {
        return _server.submitOrder(order, _authToken);
    }
}
