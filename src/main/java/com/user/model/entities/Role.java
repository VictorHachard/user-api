package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import com.user.model.entities.enums.RoleEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * This class represents a role.
 * Roles are used to define the access level of a user.
 * There can be specified in the Authorisation annotation.
 * The roles are loaded from RoleEnum, there are tree roles:
 * - ROLE_OWNER
 * - ROLE_ADMINISTRATOR
 * - ROLE_USER
 */
@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Role extends AbstractEntity {

    @Column
    @Enumerated(EnumType.STRING)
    RoleEnum role;

}
