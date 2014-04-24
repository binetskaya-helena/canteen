package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.data.AccessGroup;
import com.example.canteen.service.data.AuthToken;
import com.example.canteen.service.data.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class AccountsService {
    private final AccountsManager _accountsManager;
    private final AccessControl _accessControl;

    public AccountsService(AccountsManager accountsManager, AccessControl accessControl) {
        _accountsManager = accountsManager;
        _accessControl = accessControl;

        AccessGroup anonymousGroup = new AccessGroup("anonymous", new LinkedHashSet<String>(Arrays.asList(new String[] {
                Facade.REGISTER_USER_ACTION,
                Facade.GET_CURRENT_MENU_ACTION
        })));
        _accessControl.registerAccessGroup(anonymousGroup);
        _accessControl.setDefaultGroup(anonymousGroup);

        AccessGroup clientsGroup = new AccessGroup("clients", new LinkedHashSet<String>(Arrays.asList(new String[] {
                Facade.GET_USER_ACTION,
                Facade.GET_CURRENT_MENU_ACTION,
                Facade.SUBMIT_ORDER_ACTION
        })));
        _accessControl.registerAccessGroup(clientsGroup);

        AccessGroup cooksGroup = new AccessGroup("cooks", new LinkedHashSet<String>(Arrays.asList(new String[] {
                Facade.GET_USER_ACTION,
                Facade.GET_CURRENT_MENU_ACTION,
                Facade.CREATE_MENU_ACTION,
                Facade.REMOVE_MENU_ACTION,
                Facade.GET_ORDERED_DISHES_ACTION
        })));
        _accessControl.registerAccessGroup(cooksGroup);
    }

    public AuthToken authenticate(String name, String password) {
        User user = _accountsManager.getUser(name);
        if (null != user && _accountsManager.authenticate(user, password)) {
            return _accessControl.createAuthToken(user);

        } else {
            throw new Facade.NotAuthorizedException();
        }
    }

    public void registerClient(String name, String password) {
        User user = new User(null, name);
        user = _accountsManager.addUser(user, password);
        _accessControl.setPermissions(user, _accessControl.getAccessGroup("clients"));
    }
}
