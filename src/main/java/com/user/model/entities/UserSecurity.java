package com.user.model.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserSecurity implements Serializable {

    @Id
    @GeneratedValue
    long id;

    /*@OneToOne
    User user;*/

    @Column(unique = true, nullable = false)
    String username;

    @OneToMany
    List<Password> passwordList = new ArrayList<>();

    @Column
    String firstName;

    @Column
    String middleName;

    @Column
    String lastName;

    @Column
    @Temporal(TemporalType.DATE)
    Date birth;

    @OneToMany()
    List<CookieRemember> cookieList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> permissionList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    List<Group> groupList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    List<Email> emailList = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    PrivacyEnum privacy;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date lastConnection;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    public void addPassword(Password... passwords) {
        passwordList.addAll(Arrays.asList(passwords));
    }

    public void addEmail(Email... emails) {
        emailList.addAll(Arrays.asList(emails));
    }

}
