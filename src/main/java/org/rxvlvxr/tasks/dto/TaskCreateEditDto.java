package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for creating and editing tasks, with different validation rules for Admin and User roles")
public class TaskCreateEditDto {

    @NotBlank(groups = {ValidationGroups.Admin.class})
    @Schema(
            description = "Title of the task. Only Admin can set it.",
            example = "Task title",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    String title;

    @Schema(
            description = "Description of the task. Only Admin can set it.",
            example = "Task description",
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    String description;

    @NotNull(groups = {ValidationGroups.Admin.class, ValidationGroups.User.class})
    @Schema(
            description = "Status ID of the task. Required for Admin and User.",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    Integer statusId;

    @NotNull(groups = {ValidationGroups.Admin.class})
    @Schema(
            description = "Priority ID of the task. Only Admin can set it.",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    Integer priorityId;

    @NotNull(groups = {ValidationGroups.Admin.class})
    @Schema(
            description = "Author ID of the task. Only Admin can set it.",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    UUID authorId;

    @Schema(
            description = "Performer ID of the task. Only Admin can set it.",
            example = "123e4567-e89b-12d3-a456-426614174001",
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    UUID performerId;

    @Schema(
            description = "New comment to be added to the task. Each comment is created as a new entry.",
            example = "This is a new comment",
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    String comment;

}
