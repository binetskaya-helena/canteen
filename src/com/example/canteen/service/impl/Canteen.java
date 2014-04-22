package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.TimeService;
import com.example.canteen.service.data.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Canteen implements Facade {
    private final AccountsManager _accountsManager;
    private final OrdersProcessor _ordersProcessor;
    private final MenuSchedule _menuSchedule;
    private final OrderingService _orderingService;

    private Map<String, User> _users = new LinkedHashMap<String, User>();

    public Canteen(TimeService timeService) {
        _accountsManager = new AccountsManager();
        _ordersProcessor = new OrdersProcessor();
        _menuSchedule = new MenuSchedule(timeService);
        _orderingService = new OrderingService(_menuSchedule, _ordersProcessor);

        // demo
        Menu menu = new Menu();
        menu.addDish(new Dish("Potatoes", "Delicious potatoes", new BigDecimal(3.95)));
        menu.addDish(new Dish("Chicken", "Yong chicken", new BigDecimal(4.50)));
        menu.addDish(new Dish("Tapas", "Tapas with Bull's tail", new BigDecimal(5.75)));
        menu.setPublishingDate(new Date(timeService.now().getTime() - 1000));
        menu.setOrderingDeadline(new Date(timeService.now().getTime() + 3600000));
        menu.setValidThrough(new Date(timeService.now().getTime() + 3700000));
        _menuSchedule.schedule(menu);
    }

    @Override public AuthToken authenticate(String name, String password) throws NotAuthorizedException {
        User user = _accountsManager.getUser(name);
        if (null != user && _accountsManager.authenticate(user, password)) {
            AuthToken token = new AuthToken(name + password);
            _users.put(token.token, user);
            return token;

        } else {
            throw new NotAuthorizedException();
        }
    }

    @Override public void registerUser(String name, String password) throws DomainError {
        User user = new User(null, name);
        _accountsManager.addUser(user, password);
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
    public PublishingDetails getCurrentMenu(AuthToken authToken) {
        return _menuSchedule.currentMenu();
    }

    @Override
    public Order submitOrder(final Order order, AuthToken authToken) throws NotAuthorizedException {
        return performAuthorized(authToken, new AuthorizedAction<Order>() {
            @Override
            public Order perform() {
                return _orderingService.submitOrder(order);
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
