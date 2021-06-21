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
public class Image extends AbstractEntity {

    @Column()
    String url;

}