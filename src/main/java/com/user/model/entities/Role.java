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
public class Role implements Serializable  {

    @Id
    @GeneratedValue
    long id;

    @Column
    @Enumerated(EnumType.STRING)
    RoleEnum role;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

}
