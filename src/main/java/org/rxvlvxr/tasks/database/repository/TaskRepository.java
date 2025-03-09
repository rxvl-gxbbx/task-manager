package org.rxvlvxr.tasks.database.repository;

import org.rxvlvxr.tasks.database.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID>,
        QuerydslPredicateExecutor<Task> {
}
