package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This class represents a session.
 * A session is a connection between the front end and the back end.
 * It also stores if this session need to persist longer like a connection cookie.
 */
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
