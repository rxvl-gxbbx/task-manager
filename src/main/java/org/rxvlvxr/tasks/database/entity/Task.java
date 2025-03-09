package org.rxvlvxr.tasks.database.entity;

import com.querydsl.core.types.dsl.ComparableExpressionBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tasks", schema = "tasks")
public class Task extends BaseEntity {

    private String title;

    private String description;

    private Integer statusId;

    private Integer priorityId;

    @Column(updatable = false)
    private UUID authorId;

    private UUID performerId;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "task"
    )
    private List<Comment> comments;

    public static final String FIELD_NAME_TITLE = "title";

    public static final String FIELD_NAME_DESCRIPTION = "description";

    public static final String FIELD_NAME_STATUS_ID = "statusId";

    public static final String FIELD_NAME_PRIORITY_ID = "priorityId";

    public static final String FIELD_NAME_AUTHOR_ID = "authorId";

    public static final String FIELD_NAME_PERFORMER_ID = "performerId";

    public static final Map<String, ComparableExpressionBase<?>> COLUMNS;

    static {
        COLUMNS = new HashMap<>(BaseEntity.COLUMNS);
        COLUMNS.putAll(Map.of(
                FIELD_NAME_TITLE, QTask.task.title,
                FIELD_NAME_DESCRIPTION, QTask.task.description,
                FIELD_NAME_STATUS_ID, QTask.task.statusId,
                FIELD_NAME_PRIORITY_ID, QTask.task.priorityId,
                FIELD_NAME_AUTHOR_ID, QTask.task.authorId,
                FIELD_NAME_PERFORMER_ID, QTask.task.performerId
        ));
    }
}
