package org.rxvlvxr.tasks.dicts;

import lombok.Getter;

@Getter
public enum PriorityDictionary implements IdentifiableDictionary {
    HIGHEST(1, "Highest"),
    HIGH(2, "High"),
    DEFAULT(3, "Default"),
    LOW(4, "Low");

    private final int id;

    private final String name;

    PriorityDictionary(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PriorityDictionary fromId(int id) {
        return IdentifiableDictionary.fromId(PriorityDictionary.class, id);
    }
}
