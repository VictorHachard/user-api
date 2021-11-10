package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
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
public class Password extends AbstractEntity implements Comparable<Password> {

    /*
    Hashed using BCrypt
     */
    @Column
    String password;

    @Override
    public int compareTo(Password o) {
        return Long.compare(this.getId(), o.getId());
    }

}
