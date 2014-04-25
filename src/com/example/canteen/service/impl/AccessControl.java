package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.data.AccessGroup;
import com.example.canteen.service.data.AuthToken;
import com.example.canteen.service.data.User;

import java.util.LinkedHashMap;
import java.util.Map;

/** Controls the access of users to different resources of the system. */
public class AccessControl {
    private Map<String, User> _tokens = new LinkedHashMap<String, User>();
    private Map<String, AccessGroup> _users = new LinkedHashMap<String, AccessGroup>();
    private Map<String, AccessGroup> _groups = new LinkedHashMap<String, AccessGroup>();
    private AccessGroup _defaultGroup;

    public interface AuthorizedAction<Result> {
        Result perform();
    }

    /** Creates an auth token and associates it with the given user. */
    public AuthToken createAuthToken(User user) {
        AuthToken token = new AuthToken(makeTokenData(user));
        _tokens.put(token.token, user);
        return token;
    }

    private String makeTokenData(User user) {
        return Integer.toHexString(user.name().hashCode());
    }

    /** Registers the given access group in the system. */
    public void registerAccessGroup(AccessGroup group) {
        _groups.put(group.name(), group);
    }

    /** Returns a previously registered access group with the given name or null if there is no such group. */
    public AccessGroup getAccessGroup(String name) {
        return _groups.get(name);
    }

    /** Associates the given user with the given access group. */
    public void setPermissions(User user, AccessGroup group) {
        _users.put(user.name(), group);
    }

    /** Sets the access group of the anonymous users. */
    public void setDefaultGroup(AccessGroup group) {
        _defaultGroup = group;
    }

    /** Checks if the user with the given auth token is permitted to perform the given request. Performs the request
     * if the user passes the check.
     */
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
