package com.user.dto.commons;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.Date;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
@Setter
@Getter
public class Dto implements Comparable<Dto> {

    long id;

    Date createdAt;

    @Override
    public int compareTo(Dto o) {
        return Long.compare(this.id, o.id);
    }

    //String createdAtFormatted;

}
