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
public class Theme extends AbstractEntity {

    @Column(name = "name_a")
    String name;

    @Column
    String imageUrl;

    @Column()
    boolean active;

    @Column(name = "order_a")
    int order;

    @Column
    String primaryColor;

    @Column
    String secondaryColor;

    @Column
    String tertiaryColor;

    @Column
    String quaternaryColor;

    @Column
    String primaryTextColor;

    @Column
    String secondaryTextColor;

    @Column
    String primaryAlertSuccessColor;

    @Column
    String secondaryAlertSuccessColor;

    @Column
    String tertiaryAlertSuccessColor;

    @Column
    String primaryAlertWarningColor;

    @Column
    String secondaryAlertWarningColor;

    @Column
    String tertiaryAlertWarningColor;

    @Column
    String primaryAlertDangerColor;

    @Column
    String secondaryAlertDangerColor;

    @Column
    String tertiaryAlertDangerColor;

}
