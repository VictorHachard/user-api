package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class represents a group.
 * A group can be created and attributed to a user.
 */
@Entity
@Table(name = "group_a")
// Lombok
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Group extends AbstractEntity {

    @Column(name = "name_a")
    String name;

    @Column()
    String color;

    @Column()
    boolean active;

    @Column(name = "order_a")
    int order;

}
