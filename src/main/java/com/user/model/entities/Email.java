package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
// Lombok
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

    @Column()
    Boolean backup;

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

    @Column
    @Enumerated(EnumType.STRING)
    PrivacyEnum privacy;

}
