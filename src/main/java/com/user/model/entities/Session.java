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
 * TODO: have a cron that set active to false if the session is no longer valid.
 */
@Entity
//@Table(indexes = @Index(columnList = "authToken")) // JPA by default will create on token
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Session extends AbstractEntity {

    /**
     * Hashed using SHA-256
     */
    @Column(unique = true)
    String authToken;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date authTokenCreatedAt;

    /**
     * False if the session expire.
     * (remove the authToken if False)
     */
    @Column()
    Boolean active;

    //TODO: remove this field?
    @Column(unique = true)
    String token;

    @Column()
    String ip;

    @Column()
    String userAgent;

    @Column()
    String platform;

    @Column()
    Boolean rememberMe;

    @Column()
    Boolean onMobile;

    /**
     * The last time this session was used.
     * Any call with the associate token will update this field.
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date lastConnection;

}
