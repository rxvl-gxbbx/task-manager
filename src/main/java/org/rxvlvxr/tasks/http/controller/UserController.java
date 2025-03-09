package org.rxvlvxr.tasks.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.rxvlvxr.tasks.dto.AuthRequestDto;
import org.rxvlvxr.tasks.dto.AuthResponseDto;
import org.rxvlvxr.tasks.dto.UserCreateEditDto;
import org.rxvlvxr.tasks.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users API", description = "API for user authentication and registration")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "User login",
            description = "Authenticate a user using email and password to obtain a JWT token. The token will be used for future authentication requests.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful. JWT token returned."),
                    @ApiResponse(responseCode = "400", description = "Invalid login credentials provided.", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Unauthorized. User is not authenticated.", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "User not found.", content = @Content())
            }
    )
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User authentication request body containing email and password.")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return userService.authenticate(request);
    }

    @Operation(
            summary = "User registration",
            description = "Register a new user by providing email, password, and additional user details like first name, last name, and role.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully registered."),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided for registration.", content = @Content()),
            }
    )
    @PostMapping("/registration")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration request body containing the necessary details for registration.")
    public AuthResponseDto registration(@RequestBody UserCreateEditDto dto) {
        return userService.create(dto);
    }
}
