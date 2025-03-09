package org.rxvlvxr.tasks.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rxvlvxr.tasks.database.entity.User;
import org.rxvlvxr.tasks.dto.UserCreateEditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "modifiedAt")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract User toEntity(UserCreateEditDto dto);

}
