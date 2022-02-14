package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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

    /*
    Hashed using SHA-256
     */
    @Column(unique = true)
    String authToken;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date authTokenCreatedAt;

    /*
    If the session expire set active to False and remove the authToken.
     */
    @Column()
    Boolean active;

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

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date lastConnection;

}
