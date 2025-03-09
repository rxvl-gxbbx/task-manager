package org.rxvlvxr.tasks.database.entity;

import com.querydsl.core.types.dsl.ComparableExpressionBase;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    UUID id;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime modifiedAt;

    public static final String FIELD_NAME_ID = "id";

    public static final String FIELD_NAME_CREATED_AT = "createdAt";

    public static final String FIELD_NAME_MODIFIED_AT = "modifiedAt";

    public static final Map<String, ComparableExpressionBase<?>> COLUMNS;

    static {
        COLUMNS = new HashMap<>();

        COLUMNS.putAll(Map.of(
                FIELD_NAME_ID, QTask.task.id,
                FIELD_NAME_CREATED_AT, QTask.task.createdAt,
                FIELD_NAME_MODIFIED_AT, QTask.task.modifiedAt
        ));
    }
}
