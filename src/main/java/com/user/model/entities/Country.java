package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * This class represents an address.
 */
@Entity
@Table()
// Lombok
@EqualsAndHashCode(callSuper=true)
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Country extends AbstractEntity {

    @Column
    String country;

}
