package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.data.AccessGroup;
import com.example.canteen.service.data.AuthToken;
import com.example.canteen.service.data.User;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccessControl {
    private Map<String, User> _tokens = new LinkedHashMap<String, User>();
    private Map<String, AccessGroup> _users = new LinkedHashMap<String, AccessGroup>();
    private Map<String, AccessGroup> _groups = new LinkedHashMap<String, AccessGroup>();
    private AccessGroup _defaultGroup;

    public interface AuthorizedAction<Result> {
        Result perform();
    }

    public AuthToken createAuthToken(User user) {
        AuthToken token = new AuthToken(makeTokenData(user));
        _tokens.put(token.token, user);
        return token;
    }

    private String makeTokenData(User user) {
        return Integer.toHexString(user.name().hashCode());
    }

    public void registerAccessGroup(AccessGroup group) {
        _groups.put(group.name(), group);
    }

    public AccessGroup getAccessGroup(String name) {
        return _groups.get(name);
    }

    public void setPermissions(User user, AccessGroup group) {
        _users.put(user.name(), group);
    }

    public void setDefaultGroup(AccessGroup group) {
        _defaultGroup = group;
    }

    public <Result> Result performAuthorized(String actionName, AuthToken authToken, AuthorizedAction<Result> action) {
        AccessGroup access = null;
        if (null == authToken) {
            access = _defaultGroup;

        } else if (_tokens.containsKey(authToken.token)) {
            access = _users.get(_tokens.get(authToken.token).name());
        }
        if (null != access && access.permittedActions().contains(actionName)) {
            return action.perform();

        } else {
            throw new Facade.NotAuthorizedException();
        }
    }
}
