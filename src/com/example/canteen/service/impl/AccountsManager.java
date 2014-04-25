package com.example.canteen.service.impl;

import com.example.canteen.service.Facade;
import com.example.canteen.service.data.AuthToken;
import com.example.canteen.service.data.User;

import java.util.LinkedHashMap;
import java.util.Map;

/** Stores information about users of the system. */
public class AccountsManager {
    private static int _lastId;
    private Map<String, User> _users = new LinkedHashMap<String, User>();
    private Map<String, String> _passwords = new LinkedHashMap<String, String>();

    /** Makes a hash code of the password in a form for storing in the database. */
    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

    /** Returns the user with the given name. */
    public User getUser(String name) {
        return _users.get(name);
    }

    /** Checks the password of the given user. Returns true if the user passes the check. */
    public boolean authenticate(User user, String password) {
        return (_users.containsKey(user.name()) && _passwords.get(user.name()).equals(hashPassword(password)));
    }

    /** Registers new user in the system. Throws DomainError if a user with the same name already exists. */
    public User addUser(User user, String password) throws Facade.DomainError {
        if (!_users.containsKey(user.name())) {
            _lastId++;
            user.setId(String.format("%x", _lastId));
            _users.put(user.name(), user);
            _passwords.put(user.name(), hashPassword(password));
            return user;

        } else {
            throw new Facade.DomainError();
        }
    }
}
