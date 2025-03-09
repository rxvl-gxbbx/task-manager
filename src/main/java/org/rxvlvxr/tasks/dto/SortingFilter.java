package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Filter for sorting and paginating results")
public class SortingFilter {

    @Schema(
            description = "The field by which the results are sorted (e.g., 'createdAt', 'name')",
            example = "modifiedAt",
            defaultValue = "modifiedAt"
    )
    private String sortBy = "modifiedAt";

    @Schema(
            description = "Sorting mode: 'asc' — ascending, 'desc' — descending",
            example = "desc",
            defaultValue = "desc",
            allowableValues = {"asc", "desc"}
    )
    private String sortMode = "desc";

    @Schema(
            description = "The page number (starting from 0)",
            example = "0",
            defaultValue = "0",
            minimum = "0"
    )
    private int page = 0;

    @Schema(
            description = "The number of items per page (maximum of 100)",
            example = "20",
            defaultValue = "20",
            minimum = "1",
            maximum = "100"
    )
    private int limit = 20;
}