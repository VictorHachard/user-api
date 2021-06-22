package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import com.user.model.entities.enums.EmailPreferencesEnum;
import com.user.model.entities.enums.PrivacyEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
// Lombok
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
    Set<CookieRemember> cookieList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> permissionList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Group> groupList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Email> emailList = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    PrivacyEnum privacy;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date lastConnection;

    @Column()
    String authToken;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date authTokenCreatedAt;

    public void addPassword(Password... passwords) {
        passwordList.addAll(Arrays.asList(passwords));
    }

    public void addEmail(Email... emails) {
        emailList.addAll(Arrays.asList(emails));
    }

    public void addCookie(CookieRemember... cookieRemembers) {
        cookieList.addAll(Arrays.asList(cookieRemembers));
    }

    public void addGroup(Group... groups) {
        groupList.addAll(Arrays.asList(groups));
    }

    public void addRole(Role... roles) {
        permissionList.addAll(Arrays.asList(roles));
    }

}
