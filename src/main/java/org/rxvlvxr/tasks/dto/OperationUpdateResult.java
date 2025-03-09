package org.rxvlvxr.tasks.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(description = "Result of an operation update, including HTTP status code, object ID, and description of the result")
public class OperationUpdateResult {

    @Schema(
            description = "HTTP status code representing the result of the operation (e.g., 200 for success, 404 for not found)",
            example = "200",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int result;

    @Schema(
            description = "ID of the object that was updated. It is null if the operation failed",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Description of the result or error message (e.g., 'Object updated successfully' or error details)",
            example = "Object updated successfully",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    public OperationUpdateResult(int result, UUID id, String description) {
        this.result = result;
        this.id = id;
        this.description = description;
    }
}
