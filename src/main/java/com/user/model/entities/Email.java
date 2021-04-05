package com.user.model.entities;

import com.user.utils.Utils;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.io.Serializable;
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
public class Email implements Serializable {

    @Id
    @GeneratedValue
    long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column
    @Enumerated(EnumType.STRING)
    PriorityEnum priority;

    @Column(unique = true)
    String emailAuthToken;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date emailAuthSet;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date emailAuthAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    public void initToken() {
        this.emailAuthToken = Utils.generateNewToken(42); //TODO unique
        this.emailAuthSet = new Timestamp(System.currentTimeMillis());
        this.emailAuthAt = null;
        log.info("The validation token for " + this.email + " is " + this.emailAuthToken
                + ", the link is: http://localhost:4200/confirm/" + this.emailAuthToken);
    }

}
