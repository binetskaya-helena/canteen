package com.example.canteen.service.data;

import java.util.Set;

public class AccessGroup {
    private final String _name;
    private final Set<String> _permittedActions;

    public AccessGroup(String name, Set<String> permittedActions) {
        _name = name;
        _permittedActions = permittedActions;
    }

    public String name() {
        return _name;
    }

    public Set<String> permittedActions() {
        return _permittedActions;
    }
}
