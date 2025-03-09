package org.rxvlvxr.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing the authentication token")
public record AuthResponseDto(

        @Schema(
                description = "The authentication token issued after a successful login",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTIzNDU2Nzg5MCIsImV4cCI6MTYxODk4MTMwMH0.k8Z60X1L2Vu3HvfHmjE_HOjoUs1gBlSe6LPjRuUM1ok",
                requiredMode = Schema.RequiredMode.REQUIRED
        ) String token

) {
}