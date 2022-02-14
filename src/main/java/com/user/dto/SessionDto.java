package com.user.dto;

import com.user.dto.commons.Dto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class SessionDto extends Dto {

    String token;

    String authToken;

    String ip;

    Boolean onMobile;

    String userAgent;

    String platform;

    Date lastConnection;

}
