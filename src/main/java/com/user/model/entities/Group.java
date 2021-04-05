package com.user.model.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Group implements Serializable {

    @Id
    @GeneratedValue
    long id;

    String name;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

}
