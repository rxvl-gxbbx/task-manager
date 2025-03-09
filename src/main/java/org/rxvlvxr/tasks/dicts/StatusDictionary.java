package org.rxvlvxr.tasks.dicts;

import lombok.Getter;

@Getter
public enum StatusDictionary implements IdentifiableDictionary {
    OPEN(1, "Open"),
    IN_REVIEW(2, "In Review"),
    IN_PROGRESS(3, "In Progress"),
    DEPLOYMENT(4, "Deployment"),
    TESTING(5, "Testing"),
    CLOSED(6, "Closed"),
    SUSPENDED(7, "Suspended"),
    ;

    private final int id;

    private final String name;

    StatusDictionary(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static StatusDictionary fromId(int id) {
        return IdentifiableDictionary.fromId(StatusDictionary.class, id);
    }
}
