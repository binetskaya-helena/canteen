package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.TimeService;
import com.example.canteen.service.data.*;

import java.math.BigDecimal;
import java.util.Date;

public class Canteen implements Facade {
    private final AccountsManager _accountsManager;
    private final AccessControl _accessControl;
    private final AccountsService _accountsService;

    private final OrdersProcessor _ordersProcessor;
    private final MenuSchedule _menuSchedule;
    private final OrderingService _orderingService;

    public Canteen(TimeService timeService) {
        _accountsManager = new AccountsManager();
        _accessControl = new AccessControl();
        _accountsService = new AccountsService(_accountsManager, _accessControl);

        _ordersProcessor = new OrdersProcessor();
        _menuSchedule = new MenuSchedule(timeService);
        _orderingService = new OrderingService(_menuSchedule, _ordersProcessor, timeService);

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
        return _accountsService.authenticate(name, password);
    }

    @Override public void registerUser(final String name, final String password) throws DomainError {
        _accessControl.performAuthorized(REGISTER_USER_ACTION, null, new AccessControl.AuthorizedAction<Object>() {
            @Override
            public Object perform() {
                _accountsService.registerClient(name, password);
                return null;
            }
        });
    }

    @Override public User getUser(final String name, AuthToken authToken) throws NotAuthorizedException {
        return _accessControl.performAuthorized(GET_USER_ACTION, authToken, new AccessControl.AuthorizedAction<User>() {
            @Override
            public User perform() {
                return _accountsManager.getUser(name);
            }
        });
    }

    @Override public PublishingDetails getCurrentMenu(AuthToken authToken) {
        return _accessControl.performAuthorized(GET_CURRENT_MENU_ACTION, null, new AccessControl.AuthorizedAction<PublishingDetails>() {
            @Override
            public PublishingDetails perform() {
                return _menuSchedule.currentMenu();
            }
        });
    }

    @Override public Order submitOrder(final Order order, AuthToken authToken) throws NotAuthorizedException {
        return _accessControl.performAuthorized(SUBMIT_ORDER_ACTION, authToken, new AccessControl.AuthorizedAction<Order>() {
            @Override
            public Order perform() {
                return _orderingService.submitOrder(order);
            }
        });
    }

    @Override public Menu scheduleMenu(final Menu menu, AuthToken authToken) {
        return _accessControl.performAuthorized(CREATE_MENU_ACTION, authToken, new AccessControl.AuthorizedAction<Menu>() {
            @Override
            public Menu perform() {
                return _menuSchedule.schedule(menu);
            }
        });
    }

    @Override public void cancelMenu(final Menu menu, AuthToken authToken) {
        _accessControl.performAuthorized(REMOVE_MENU_ACTION, authToken, new AccessControl.AuthorizedAction<Object>() {
            @Override
            public Object perform() {
                _menuSchedule.remove(menu);
                return null;
            }
        });
    }
}
