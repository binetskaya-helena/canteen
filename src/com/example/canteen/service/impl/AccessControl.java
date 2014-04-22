package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.data.AuthToken;
import com.example.canteen.service.data.User;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccessControl {
    private final AccountsManager _accountsManager;
    private Map<String, User> _users = new LinkedHashMap<String, User>();

    public interface AuthorizedAction<Result> {
        Result perform();
    }

    public AccessControl(AccountsManager accountsManager) {
        _accountsManager = accountsManager;
    }

    public AuthToken authenticate(String name, String password) {
        User user = _accountsManager.getUser(name);
        if (null != user && _accountsManager.authenticate(user, password)) {
            AuthToken token = new AuthToken(name + password);
            _users.put(token.token, user);
            return token;

        } else {
            throw new Facade.NotAuthorizedException();
        }
    }

    public User getUser(final AuthToken authToken) {
        return _users.get(authToken.token);
    }

    public <Result> Result performAuthorized(AuthToken authToken, AuthorizedAction<Result> action) {
        if (null != authToken && _users.containsKey(authToken.token)) {
            return action.perform();

        } else {
            throw new Facade.NotAuthorizedException();
        }
    }
}
