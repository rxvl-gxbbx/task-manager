package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for user authentication request containing username and password")
public class AuthRequestDto {

    @Email
    @NotBlank
    @Schema(
            description = "User's email address used for authentication",
            example = "user@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String username;

    @NotBlank
    @Schema(
            description = "Password associated with the user account",
            example = "password123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String password;
}