package org.rxvlvxr.tasks.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.rxvlvxr.tasks.database.entity.Role;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for creating or editing a user, including username, password, and additional user details")
public class UserCreateEditDto extends AuthRequestDto {

    @NotBlank
    @Schema(
            description = "User's first name",
            example = "John",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String firstname;

    @NotBlank
    @Schema(
            description = "User's last name",
            example = "Doe",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String lastname;

    @NotNull
    @Schema(
            description = "User's role within the system (e.g. Admin, User, etc.)",
            example = "USER",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    Role role;
}
