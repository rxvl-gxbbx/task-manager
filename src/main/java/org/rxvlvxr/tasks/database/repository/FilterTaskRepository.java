package org.rxvlvxr.tasks.database.repository;

import org.rxvlvxr.tasks.database.entity.Task;
import org.rxvlvxr.tasks.dto.TaskFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterTaskRepository {

    List<Task> findAllByFilter(TaskFilter filter);
}
