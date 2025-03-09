package org.rxvlvxr.tasks.service;

import lombok.RequiredArgsConstructor;
import org.rxvlvxr.tasks.database.entity.*;
import org.rxvlvxr.tasks.database.repository.FilterTaskRepository;
import org.rxvlvxr.tasks.database.repository.TaskRepository;
import org.rxvlvxr.tasks.database.repository.UserRepository;
import org.rxvlvxr.tasks.dto.*;
import org.rxvlvxr.tasks.exception.ForbiddenException;
import org.rxvlvxr.tasks.exception.NotFoundException;
import org.rxvlvxr.tasks.mapper.TaskMapper;
import org.rxvlvxr.tasks.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final ValidationService validationService;

    private final UserRepository userRepository;

    private final FilterTaskRepository filterTaskRepository;

    public @NonNull TaskWrapperDto findAll(TaskFilter filter) {
        return new TaskWrapperDto(
                filterTaskRepository.findAllByFilter(filter)
                        .stream()
                        .map(taskMapper::toDto)
                        .toList()
        );
    }

    public @NonNull TaskReadDto findById(@NonNull UUID id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(ExceptionUtils.createNotFoundException(id));
    }

    @Transactional
    public @NonNull ResponseEntity<OperationUpdateResult> createOrUpdate(@Nullable UUID id, @NonNull TaskCreateEditDto dto) {
        boolean isAdmin = Role.hasAuthority(Role.ADMIN);

        validationService.validate(dto, isAdmin ? ValidationGroups.Admin.class : ValidationGroups.User.class);

        Task entity;

        if (id == null) {
            entity = new Task();
        } else {
            entity = getTaskEntity(id);
        }

        if (isAdmin) {
            taskMapper.copy(dto, entity);
            if (entity.getAuthorId() == null) entity.setAuthorId(dto.getAuthorId());
        } else if (entity.getPerformerId() == null) {
            throw new ForbiddenException("The task cannot be modified because no assignee is assigned.");
        } else {
            User user = UserService.getUserEntity(userRepository, entity.getPerformerId());

            UserDetails currentUser = getCurrentUser();

            Objects.requireNonNull(currentUser);

            if (!Objects.equals(currentUser.getUsername(), user.getUsername())) {
                throw new ForbiddenException("You are not authorized to modify this task. Only the assigned user can make changes.");
            }

            entity.setStatusId(dto.getStatusId());
        }

        if (StringUtils.hasText(dto.getComment())) {
            if (entity.getComments() == null) entity.setComments(new ArrayList<>());

            entity.getComments().add(new Comment(dto.getComment(), entity));
        }

        taskRepository.save(entity);

        HttpStatus httpStatus = id == null ? HttpStatus.CREATED : HttpStatus.OK;

        return new ResponseEntity<>(new OperationUpdateResult(
                httpStatus.value(),
                entity.getId(),
                String.format("Task with ID %s has been successfully %s.", entity.getId(), id == null ? "created" : "updated")
        ), httpStatus);
    }

    @Transactional
    public @NonNull OperationUpdateResult delete(@NonNull UUID id) {
        Task entity = getTaskEntity(id);

        taskRepository.delete(entity);

        return new OperationUpdateResult(
                HttpStatus.OK.value(),
                entity.getId(),
                String.format("Task with ID %s has been successfully deleted.", id)
        );
    }

    @Transactional
    public @NonNull OperationUpdateResult updateComment(@NonNull UUID taskId, @NonNull UUID commentId, @NonNull CommentEditDto dto) {
        validationService.validate(dto);

        Task entity = getTaskEntity(taskId);

        if (entity.getComments() == null) {
            throw new NotFoundException("Cannot update comment: no comments found for task with ID " + taskId);
        }

        Map<UUID, Comment> uuidCommentMap = entity.getComments().stream()
                .collect(Collectors.toMap(BaseEntity::getId, v -> v));

        Comment comment = Optional.ofNullable(uuidCommentMap.get(commentId))
                .orElseThrow(ExceptionUtils.createNotFoundException(commentId, "Comment"));

        comment.setComment(dto.getComment());

        taskRepository.save(entity);

        return new OperationUpdateResult(
                HttpStatus.OK.value(),
                entity.getId(),
                String.format("Comment with ID %s has been successfully updated.", commentId)
        );
    }

    @Transactional
    public @NonNull OperationUpdateResult deleteComment(@NonNull UUID taskId, @NonNull UUID commentId) {
        Task entity = getTaskEntity(taskId);

        if (entity.getComments() == null) {
            throw new NotFoundException("Cannot delete comment: no comments found for task with ID " + taskId);
        }

        Map<UUID, Comment> uuidCommentMap = entity.getComments().stream()
                .collect(Collectors.toMap(BaseEntity::getId, v -> v));

        Comment comment = Optional.ofNullable(uuidCommentMap.get(commentId))
                .orElseThrow(ExceptionUtils.createNotFoundException(commentId, "Comment"));

        entity.getComments().remove(comment);

        taskRepository.delete(entity);

        return new OperationUpdateResult(
                HttpStatus.OK.value(),
                entity.getId(),
                String.format("Comment with ID %s has been successfully deleted.", commentId)
        );
    }

    private @NonNull Task getTaskEntity(@NonNull UUID id) {
        return getTaskEntity(taskRepository, id);
    }

    public static @NonNull Task getTaskEntity(@NonNull TaskRepository taskRepository, @NonNull UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(ExceptionUtils.createNotFoundException(id));
    }

    private @Nullable UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
