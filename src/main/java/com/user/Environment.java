package com.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Environment {

    public static final String DATA_FOLDER = !RunApplication.PRODUCTION ?
            "C:/images/" : //dev
            "/home/ubuntu/data/images/"; //prod
    public static final List<String> ACCESS_CONTROL_ALLOW_ORIGIN_URL = !RunApplication.PRODUCTION ?
            new ArrayList<>(List.of( //dev
                    "http://localhost:4200")) :
            new ArrayList<>(Arrays.asList( //prod
                    "https://141.94.251.48",
                    "https://vps.ypc.yt",
                    "https://api.vps.ypc.yt",
                    "https://www.vps.ypc.yt",
                    "http://141.94.251.48",
                    "http://vps.ypc.yt",
                    "http://api.vps.ypc.yt",
                    "http://www.vps.ypc.yt"));
    public static final String EMAIL_USERNAME = !RunApplication.PRODUCTION ?
            "" : //dev
            ""; //prod
    public static final String EMAIL_PASSWORD = !RunApplication.PRODUCTION ?
            "" : //dev
            ""; //prod
    public static final String REDIRECT_FRONT_END_URL = !RunApplication.PRODUCTION ?
            "http://localhost:4200/" : //dev
            "http://www.vps.ypc.yt/"; //prod

}