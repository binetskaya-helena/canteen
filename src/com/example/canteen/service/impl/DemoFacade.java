package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.data.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DemoFacade implements Facade {

    private Map<String, User> _users = new LinkedHashMap<String, User>();
    private Map<String, String> _passwords = new LinkedHashMap<String, String>();

    private final OrdersProcessor _ordersProcessor = new OrdersProcessor();

    @Override public AuthToken authenticate(String name, String password) throws NotAuthorizedException {
        if (_passwords.containsKey(name) && _passwords.get(name).equals(password)) {
            AuthToken token = new AuthToken(name + password);
            User user = new User(name, name);
            _users.put(token.token, user);
            return token;

        } else {
            throw new NotAuthorizedException();
        }
    }

    @Override public void registerUser(String name, String password) throws DomainError {
        if (!_passwords.containsKey(name)) {
            _passwords.put(name, password);

        } else {
            throw new DomainError();
        }
    }

    @Override public User getUser(final AuthToken authToken) throws NotAuthorizedException {
        return performAuthorized(authToken, new AuthorizedAction<User>() {
            @Override
            public User perform() {
                return _users.get(authToken.token);
            }
        });
    }

    @Override
    public Menu getCurrentMenu(AuthToken authToken) {
        return new Menu(new ArrayList<Dish>(), new Date(13500000));
    }

    @Override
    public Order submitOrder(final Order order, AuthToken authToken) throws NotAuthorizedException {
        return performAuthorized(authToken, new AuthorizedAction<Order>() {
            @Override
            public Order perform() {
                return _ordersProcessor.submit(order);
            }
        });
    }

    private <Result> Result performAuthorized(AuthToken authToken, AuthorizedAction<Result> action) throws NotAuthorizedException {
        if (null != authToken && _users.containsKey(authToken.token)) {
            return action.perform();

        } else {
            throw new NotAuthorizedException();
        }
    }

    private interface AuthorizedAction<Result> {
        Result perform();
    }
}
