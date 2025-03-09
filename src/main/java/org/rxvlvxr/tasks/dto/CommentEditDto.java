package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO for editing a comment")
public class CommentEditDto {

    @NotBlank
    @Schema(
            description = "The new text content of the comment",
            example = "This is the updated comment.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String comment;
}