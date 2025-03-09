package org.rxvlvxr.tasks.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users", schema = "tasks")
public class User extends BaseEntity {

    private String firstname;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

}
