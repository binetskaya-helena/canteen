package com.example.canteen.service.data;

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

    public String id() {
        return _id;
    }

    public String name() {
        return _name;
    }
}
