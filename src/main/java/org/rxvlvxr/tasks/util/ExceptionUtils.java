package org.rxvlvxr.tasks.util;

import org.rxvlvxr.tasks.exception.NotFoundException;
import org.springframework.lang.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public class ExceptionUtils {

    public static Supplier<NotFoundException> createNotFoundException(UUID id) {
        return createNotFoundException(id, null);
    }

    public static Supplier<NotFoundException> createNotFoundException(UUID id, @Nullable String entityName) {
        return () -> new NotFoundException((entityName == null ? "Task" : entityName) + " with ID " + id + " not found.");
    }
}
