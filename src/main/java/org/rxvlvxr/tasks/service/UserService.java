package org.rxvlvxr.tasks.service;

import lombok.RequiredArgsConstructor;
import org.rxvlvxr.tasks.config.JwtService;
import org.rxvlvxr.tasks.database.entity.User;
import org.rxvlvxr.tasks.database.repository.UserRepository;
import org.rxvlvxr.tasks.dto.AuthRequestDto;
import org.rxvlvxr.tasks.dto.AuthResponseDto;
import org.rxvlvxr.tasks.dto.UserCreateEditDto;
import org.rxvlvxr.tasks.exception.NotFoundException;
import org.rxvlvxr.tasks.mapper.UserMapper;
import org.rxvlvxr.tasks.util.ExceptionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    @Transactional
    public AuthResponseDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(this::getToken)
                .map(AuthResponseDto::new)
                .orElseThrow();
    }

    private String getToken(User user) {
        return jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ));
    }

    public AuthResponseDto authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));

        User user = getUserEntity(request.getUsername());

        String token = getToken(user);

        return new AuthResponseDto(token);
    }

    public static User getUserEntity(UserRepository userRepository, UUID id) {
        return userRepository.findById(id)
                .orElseThrow(ExceptionUtils.createNotFoundException(id, "User"));
    }

    private User getUserEntity(String username) {
        return getUserEntity(userRepository, username);
    }

    public static User getUserEntity(UserRepository userRepository, String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username " + username + " not found."));
    }
}
