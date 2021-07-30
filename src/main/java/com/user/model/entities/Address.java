package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table()
// Lombok
@EqualsAndHashCode(callSuper=true)
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Address extends AbstractEntity {

    @Column
    String alias;

    @Column
    Boolean _default;

    @Column
    String name;

    @Column
    String building; // Building, floor...

    @Column
    String street; // Street, house number, box number

    @Column
    String postcode; // Postcode and name of municipality

}
