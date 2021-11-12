package com.user.model.entities.commons;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class is the base class for all entities.
 * The base class contains the common attributes for all entities like id and creation date.
 */
@MappedSuperclass
// Lombok
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    long id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

}
