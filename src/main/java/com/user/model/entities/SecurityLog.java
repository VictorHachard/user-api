package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import com.user.model.entities.enums.SecurityLogEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * This class represents log for a user.
 */
@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class SecurityLog extends AbstractEntity implements Comparable<SecurityLog> {

    @Column
    @Enumerated(EnumType.STRING)
    SecurityLogEnum securityLog;

    @Column
    String info;

    @JoinColumn()
    @ManyToOne
    UserSecurity userSecurity;

    @JoinColumn()
    @ManyToOne
    Session session;

    @Override
    public int compareTo(SecurityLog o) {
        return Long.compare(this.getId(), o.getId());
    }

}