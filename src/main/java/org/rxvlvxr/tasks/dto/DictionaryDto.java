package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing a dictionary entry, such as status values (using numeric IDs instead of strings to improve performance and consistency)")
public record DictionaryDto(

        @Schema(
                description = "Unique identifier for the dictionary entry (e.g., status ID)",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        ) int id,

        @Schema(
                description = "Human-readable name or description of the dictionary entry (e.g., 'Open', 'In Review')",
                example = "Open",
                requiredMode = Schema.RequiredMode.REQUIRED
        ) String name

) {
}