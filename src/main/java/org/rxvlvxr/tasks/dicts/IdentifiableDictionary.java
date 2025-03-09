package org.rxvlvxr.tasks.dicts;

import java.util.Arrays;

public interface IdentifiableDictionary {

    int getId();

    static <E extends Enum<E> & IdentifiableDictionary> E fromId(Class<E> enumClass, int id) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with id " + id));
    }
}
