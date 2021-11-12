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
 * This class represents a recovery code.
 */
@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RecoveryCode extends AbstractEntity {

    //TODO DO NOT STORE THE PERSISTENT TOKEN IN YOUR DATABASE, ONLY A HASH OF IT!
    @Column(unique = true)
    String token;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date tokenUsedAt;

    @Column()
    Boolean used;

}
