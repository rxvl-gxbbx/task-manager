package org.rxvlvxr.tasks.database.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.rxvlvxr.tasks.database.entity.Task;
import org.rxvlvxr.tasks.database.querydsl.QPredicates;
import org.rxvlvxr.tasks.dto.TaskFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.rxvlvxr.tasks.database.entity.QTask.task;
import static org.rxvlvxr.tasks.database.entity.QUser.user;

@Service
@RequiredArgsConstructor
public class FilterTaskRepositoryImpl implements FilterTaskRepository {

    private final EntityManager entityManager;

    @Override
    public List<Task> findAllByFilter(TaskFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.getAuthorId(), task.authorId::eq)
                .add(filter.getPerformerId(), task.performerId::eq)
                .build();

        return new JPAQuery<Task>(entityManager)
                .select(task)
                .from(task)
                .leftJoin(user)
                .on(task.performerId.eq(user.id))
                .where(predicate)
                .orderBy(getOrderBy(filter.getSortBy(), filter.getSortMode()))
                .offset((long) filter.getPage() * filter.getLimit())
                .limit(filter.getLimit())
                .fetch();
    }

    private OrderSpecifier<?> getOrderBy(String sortBy, String sortMode) {
        ComparableExpressionBase<?> expressionBase = Task.COLUMNS.get(sortBy);

        return Objects.equals(sortMode, "asc") ? expressionBase.asc() : expressionBase.desc();
    }
}
