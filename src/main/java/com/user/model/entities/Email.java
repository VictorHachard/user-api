package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import com.user.model.entities.enums.PriorityEnum;
import com.user.utils.Utils;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
// Lombok
@Log
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Email extends AbstractEntity {

    @Column(unique = true, nullable = false)
    String email;

    @Column
    @Enumerated(EnumType.STRING)
    PriorityEnum priority;

    @Column(name = "email_auth")
    Boolean emailConfirmed;

    @Column(unique = true)
    String emailConfirmedToken;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date emailConfirmedSet;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date emailConfirmedAt;

}
