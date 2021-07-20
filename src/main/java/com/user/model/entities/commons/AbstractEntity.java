package com.user.model.entities.commons;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
