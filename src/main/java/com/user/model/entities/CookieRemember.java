package com.user.model.entities;

import com.user.model.entities.commmons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class CookieRemember extends AbstractEntity {

    @Column(unique = true)
    String token;

}
