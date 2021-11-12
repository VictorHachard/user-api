package com.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Environment {

    private static Environment instance = new Environment();

    public String NAME;
    public String DOMAIN_NAME;
    public String DATA_FOLDER;
    public List<String> ACCESS_CONTROL_ALLOW_ORIGIN_URL;
    public String EMAIL_USERNAME;
    public String EMAIL_PASSWORD;
    public String REDIRECT_FRONT_END_URL;

    private Environment() {
        RunEnum runEnum = RunApplication.runEnum;
        switch (runEnum) {
            case DEVELOPMENT:
                NAME = "Localhost";
                DOMAIN_NAME = "http://localhost:4200";
                DATA_FOLDER = "C:/images/";
                ACCESS_CONTROL_ALLOW_ORIGIN_URL = new ArrayList<>(List.of(
                        "http://localhost:4200"));
                EMAIL_USERNAME = "";
                EMAIL_PASSWORD = "";
                REDIRECT_FRONT_END_URL = "http://localhost:4200/";
                break;
            case PRODUCTION:
            case TEST_RUN:
                NAME = "User Application";
                DOMAIN_NAME = "https://www.vps.ypc.yt";
                DATA_FOLDER = "/home/ubuntu/data/images/";
                ACCESS_CONTROL_ALLOW_ORIGIN_URL = new ArrayList<>(Arrays.asList(
                        "https://141.94.251.48",
                        "https://vps.ypc.yt",
                        "https://api.vps.ypc.yt",
                        "https://www.vps.ypc.yt",
                        "http://141.94.251.48",
                        "http://vps.ypc.yt",
                        "http://api.vps.ypc.yt",
                        "http://www.vps.ypc.yt"));
                EMAIL_USERNAME = "";
                EMAIL_PASSWORD = "";
                REDIRECT_FRONT_END_URL = "https://www.vps.ypc.yt/";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + runEnum);
        }
    }

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }
        return instance;
    }

}