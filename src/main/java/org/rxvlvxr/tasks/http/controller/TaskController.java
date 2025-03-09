package org.rxvlvxr.tasks.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.rxvlvxr.tasks.dto.*;
import org.rxvlvxr.tasks.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Tasks API", description = "API for managing tasks, including CRUD operations and comment management")
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
            summary = "Retrieve all tasks",
            description = "Fetch a list of tasks based on the provided filters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks"),
                    @ApiResponse(responseCode = "400", description = "Bad request due to invalid filters", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden access. User does not have necessary permissions.", content = @Content())
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public TaskWrapperDto findAll(TaskFilter filter) {
        return taskService.findAll(filter);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
            summary = "Retrieve task by ID",
            description = "Fetch details of a specific task using its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved task"),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden access. User does not have necessary permissions.", content = @Content())
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public TaskReadDto findById(@PathVariable UUID id) {
        return taskService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Create a new task",
            description = "Create a new task with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid task data provided", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden access. User does not have necessary permissions.", content = @Content())
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<OperationUpdateResult> create(@RequestBody TaskCreateEditDto dto) {
        return taskService.createOrUpdate(null, dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
            summary = "Update an existing task",
            description = "Update the task with the given ID. Users can only update the status and add a new comment, not update existing ones.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated successfully, new comment added"),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Invalid task data provided", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden access. User does not have necessary permissions.", content = @Content())
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<OperationUpdateResult> update(@PathVariable UUID id, @RequestBody TaskCreateEditDto dto) {
        return taskService.createOrUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Delete a task",
            description = "Delete a task using its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden access. User does not have necessary permissions.", content = @Content())
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public OperationUpdateResult delete(@PathVariable UUID id) {
        return taskService.delete(id);
    }

    @PutMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Update task comment",
            description = "Only administrators can update an existing comment on a task. Users can only add new comments, not update existing ones.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Task or comment not found", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden access. User does not have necessary permissions.", content = @Content())
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public OperationUpdateResult updateComment(@PathVariable UUID taskId, @PathVariable UUID commentId, @RequestBody CommentEditDto dto) {
        return taskService.updateComment(taskId, commentId, dto);
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Delete task comment",
            description = "Delete a specific comment on a task.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Task or comment not found", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden access. User does not have necessary permissions.", content = @Content())
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public OperationUpdateResult deleteComment(@PathVariable UUID taskId, @PathVariable UUID commentId) {
        return taskService.deleteComment(taskId, commentId);
    }
}
