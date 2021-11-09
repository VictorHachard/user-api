package com.user.model.entities;

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
public class RecoveryCode {

    @Column(unique = true)
    String token;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date tokenUsedAt;

    @Column()
    Boolean used;

}
