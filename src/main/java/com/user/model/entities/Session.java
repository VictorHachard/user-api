package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import com.user.model.entities.enums.EmailPreferencesEnum;
import com.user.model.entities.enums.PriorityEnum;
import com.user.model.entities.enums.PrivacyEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Session extends AbstractEntity {

    /*
    Hashed using SHA-256
     */
    @Column()
    String authToken;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date authTokenCreatedAt;

    @Column()
    String token;

}
