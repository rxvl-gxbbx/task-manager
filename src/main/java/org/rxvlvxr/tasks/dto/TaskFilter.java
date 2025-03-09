package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Schema(description = "Filter for tasks, including sorting and additional filters like author and performer")
public class TaskFilter extends SortingFilter {

    @Schema(
            description = "ID of the author of the task",
            example = "123e4567-e89b-12d3-a456-426614174000",
            type = "string"
    )
    private UUID authorId;

    @Schema(
            description = "ID of the performer of the task",
            example = "123e4567-e89b-12d3-a456-426614174001",
            type = "string"
    )
    private UUID performerId;
}