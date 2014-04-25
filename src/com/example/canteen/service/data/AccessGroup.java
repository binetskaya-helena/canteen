package com.example.canteen.service.data;

import java.util.Set;

/** Contains the information about a group of users with some privileges. */
public class AccessGroup {
    private final String _name;
    private final Set<String> _permittedActions;

    public AccessGroup(String name, Set<String> permittedActions) {
        _name = name;
        _permittedActions = permittedActions;
    }

    /** The name of the group. */
    public String name() {
        return _name;
    }

    /** The list of actions permitted for the group. */
    public Set<String> permittedActions() {
        return _permittedActions;
    }
}
