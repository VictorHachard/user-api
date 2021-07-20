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

    @ManyToMany(fetch = FetchType.EAGER)
    Set<UserSecurity> blockedUserSecurity = new HashSet<>();

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

    @Column()
    Boolean twoFactorEmail;

    @Column()
    String twoFactorEmailCode;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    Date twoFactorEmailCreatedAt;

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
