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

/**
 * This class represents a user.
 */
@Entity
//@Table(indexes = { // JPA by default will create index on username and token
//        @Index(columnList = "username"),
//        @Index(columnList = "passwordResetToken"),
//})
// Lombok
@EqualsAndHashCode(callSuper=true)
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserSecurity extends AbstractEntity {

    /*@OneToOne
    User user;*/

    @Column(unique = true, nullable = false)
    String username;

    /*
    Hashed using SHA-256
     */
    @Column(unique = true)
    String passwordResetToken;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date passwordResetSet;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date passwordResetAt;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Password> passwordList = new HashSet<>();

    @Column
    String firstName;

    @Column
    String middleName;

    @Column
    String lastName;

    @Column
    String url;

    @Column
    String biography;

    @Column
    @Temporal(TemporalType.DATE)
    Date birth;

    @JoinColumn()
    @ManyToOne
    Theme theme;

    @Column
    String profileImageUrl;

    @Column
    EmailPreferencesEnum emailPreferences;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Session> sessionList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> permissionList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Group> groupList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Email> emailList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Address> addressList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<UserSecurity> blockedUserSecurity = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    Set<RecoveryCode> recoveryCodeList = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    PrivacyEnum privacy;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date lastConnection;

    @Column()
    Boolean twoFactorEmail;

    //TODO DO NOT STORE THE PERSISTENT CODE IN YOUR DATABASE, ONLY A HASH OF IT!
    @Column()
    String twoFactorEmailCode;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date twoFactorEmailCreatedAt;

    public void addSession(Session... sessions) {
        sessionList.addAll(Arrays.asList(sessions));
    }

    public void addPassword(Password... passwords) {
        passwordList.addAll(Arrays.asList(passwords));
    }

    public void addEmail(Email... emails) {
        emailList.addAll(Arrays.asList(emails));
    }

    public void addAddress(Address... addresses) {
        addressList.addAll(Arrays.asList(addresses));
    }

    public void addGroup(Group... groups) {
        groupList.addAll(Arrays.asList(groups));
    }

    public void addRole(Role... roles) {
        permissionList.addAll(Arrays.asList(roles));
    }

    public void addBlockedUserSecurity(UserSecurity... userSecurities) {
        blockedUserSecurity.addAll(Arrays.asList(userSecurities));
    }

    /* Methods */

    public Email getPrincipalEmail() {
        return this.getEmailList().stream().filter(email -> {return email.getPriority().equals(PriorityEnum.PRIMARY);}).collect(Collectors.toList()).get(0);
    }

    public boolean hasEmail(Email e) {
        boolean contain = false;
        for (Email ee : this.getEmailList()) {if (ee.getEmail().equals(e.getEmail())) {contain = true; break;}}
        return contain;
    }

    public boolean hasEmail(String e) {
        boolean contain = false;
        for (Email ee : this.getEmailList()) {if (ee.getEmail().equals(e)) {contain = true; break;}}
        return contain;
    }

}
