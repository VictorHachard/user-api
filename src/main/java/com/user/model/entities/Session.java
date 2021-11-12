package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Session extends AbstractEntity {

    /*
    Hashed using SHA-256
     */
    @Column(unique = true)
    String authToken;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date authTokenCreatedAt;

    @Column(unique = true)
    String token;

    @Column()
    String ip;

    @Column()
    Boolean rememberMe;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date lastConnection;

}
