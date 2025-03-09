package org.rxvlvxr.tasks.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.rxvlvxr.tasks.database.entity.Task;
import org.rxvlvxr.tasks.database.entity.User;
import org.rxvlvxr.tasks.database.repository.UserRepository;
import org.rxvlvxr.tasks.dicts.PriorityDictionary;
import org.rxvlvxr.tasks.dicts.StatusDictionary;
import org.rxvlvxr.tasks.dto.DictionaryDto;
import org.rxvlvxr.tasks.dto.TaskCreateEditDto;
import org.rxvlvxr.tasks.dto.TaskReadDto;
import org.rxvlvxr.tasks.dto.UserReadDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "modifiedAt")
    @Mapping(ignore = true, target = "authorId")
    public abstract void copy(TaskCreateEditDto dto, @MappingTarget Task entity);

    public TaskReadDto toDto(Task entity) {
        TaskReadDto dto = new TaskReadDto();

        List<UUID> ids = new ArrayList<>();

        Objects.requireNonNull(entity.getAuthorId());

        ids.add(entity.getAuthorId());
        if (entity.getPerformerId() != null) ids.add(entity.getPerformerId());

        Map<UUID, User> uuidUserMap = userRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(User::getId, v -> v));

        User author = uuidUserMap.get(entity.getAuthorId());
        User performer = Objects.requireNonNullElseGet(uuidUserMap.get(entity.getPerformerId()), User::new);

        dto.setAuthor(UserReadDto.builder()
                .id(author.getId())
                .firstName(author.getFirstname())
                .lastName(author.getLastname())
                .build());

        dto.setPerformer(UserReadDto.builder()
                .id(performer.getId())
                .firstName(performer.getFirstname())
                .lastName(performer.getLastname())
                .build());

        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setModifiedAt(entity.getModifiedAt());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());

        DictionaryDto status = null;
        DictionaryDto priority = null;

        if (entity.getStatusId() != null) {
            StatusDictionary statusDictionary = StatusDictionary.fromId(entity.getStatusId());

            status = new DictionaryDto(statusDictionary.getId(), statusDictionary.getName());
        }

        if (entity.getPriorityId() != null) {
            PriorityDictionary priorityDictionary = PriorityDictionary.fromId(entity.getPriorityId());

            priority = new DictionaryDto(priorityDictionary.getId(), priorityDictionary.getName());
        }

        dto.setStatus(status);
        dto.setPriority(priority);

        return dto;
    }
}
