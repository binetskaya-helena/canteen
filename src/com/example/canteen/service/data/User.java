package com.example.canteen.service.data;

/** Contains information about a user of the system. */
public class User {
    private String _id;
    private final String _name;

    public User(String id, String name) {
        _id = id;
        _name = name;
    }

    public void setId(String id) {
        _id = id;
    }

    /** The internal system identifier of this user. */
    public String id() {
        return _id;
    }

    /** The name of the user in the system. */
    public String name() {
        return _name;
    }
}
