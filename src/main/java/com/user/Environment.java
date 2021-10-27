package com.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Environment {

    //Dev
    public static final String DATA_FOLDER = "C:/images/";
    public static final List<String> ACCESS_CONTROL_ALLOW_ORIGIN_URL = new ArrayList<>(List.of("http://localhost:4200"));

    //Prod
    //public static final String DATA_FOLDER = "/home/ubuntu/data/images/";
    //public static final List<String> ACCESS_CONTROL_ALLOW_ORIGIN_URL = new ArrayList<>(Arrays.asList("http://141.94.251.48", "http://vps.ypc.yt", "http://www.vps.ypc.yt"));

}