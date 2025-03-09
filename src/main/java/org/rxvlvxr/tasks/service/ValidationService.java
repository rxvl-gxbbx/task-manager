package org.rxvlvxr.tasks.service;

import com.alibaba.fastjson2.JSON;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationService {

    private final Validator validator;

    public void validate(Object dto) {
        if (dto == null) {
            throw new ValidationException(JSON.toJSONString("Object must not be null"));
        }
        validate(dto, new Class[]{});
    }

    public void validate(Object dto, @Nullable Class<?>... groups) {
        Set<ConstraintViolation<Object>> errors;

        if (groups == null || groups.length == 0) {
            errors = validator.validate(dto);
        } else {
            errors = validator.validate(dto, groups);
        }

        List<String> err = new ArrayList<>();

        if (!errors.isEmpty()) {
            for (ConstraintViolation<Object> er : errors) {
                err.add(er.getPropertyPath().toString() + " " + er.getMessage());
            }

            throw new ValidationException(JSON.toJSONString(String.join(", ", err)));
        }
    }

    public void validate(Collection<?> dtoList) {
        dtoList.forEach(dto -> validate(dto, new Class[]{}));
    }

    public void validate(Collection<?> dtoList, @Nullable Class<?>... groups) {
        if (dtoList == null || dtoList.isEmpty()) {
            throw new ValidationException(JSON.toJSONString("Collection must not be empty"));
        }
        dtoList.forEach(it -> validate(it, groups));
    }
}
