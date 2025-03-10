package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Wrapper DTO for a list of tasks")
public class TaskWrapperDto {

    @Schema(
            description = "List of tasks",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private List<TaskReadDto> tasks;
}