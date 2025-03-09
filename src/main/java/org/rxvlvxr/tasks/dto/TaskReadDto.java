package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for reading task details")
public class TaskReadDto {

    @Schema(
            description = "Unique identifier of the task",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    UUID id;

    @Schema(
            description = "Title of the task",
            example = "Task title",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    String title;

    @Schema(
            description = "Description of the task",
            example = "Task description",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    String description;

    @Schema(
            description = "Status of the task, represented as a dictionary",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    DictionaryDto status;

    @Schema(
            description = "Priority of the task, represented as a dictionary",
            example = "2",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    DictionaryDto priority;

    @Schema(
            description = "Author of the task",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    UserReadDto author;

    @Schema(
            description = "Performer of the task",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    UserReadDto performer;

    @Schema(
            description = "Date and time when the task was created",
            example = "2025-03-09T12:34:56",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the task was last modified",
            example = "2025-03-09T12:34:56",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    LocalDateTime modifiedAt;
}