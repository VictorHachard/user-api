package com.user.model.entities;

import com.user.model.entities.commons.AbstractEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table()
// Lombok
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class HtmlTextHistory extends AbstractEntity {

    @Column()
    String name;

    @OneToMany(fetch = FetchType.EAGER)
    Set<HtmlText> htmlHistory = new HashSet<>();

    public void addHtmlText(HtmlText... htmlTexts) {
        htmlHistory.addAll(Arrays.asList(htmlTexts));
    }

}
