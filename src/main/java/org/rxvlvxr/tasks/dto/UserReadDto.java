package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for reading user details")
public class UserReadDto {

    @Schema(
            description = "Unique identifier of the user",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    UUID id;

    @Schema(
            description = "User's first name",
            example = "John",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    String lastName;
}