package com.user.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Utils {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    static final String AB = "0123456789";

    /**
     * 24 -> 32 char / 48 -> 60 char So for 24 bytes from the above example you get the 32 chars.
     * @return  random string in base64 encoding with 32 chars.
     * In Base64 encoding every char encodes 6 bits of the data.
     * https://stackoverflow.com/questions/13992972/how-to-create-a-authentication-token-using-java
     */
    public static String generateNewToken(int i) {
        byte[] randomBytes = new byte[i];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String randomNumber(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(secureRandom.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static List<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static String lowerFirstChar(String str) {
        String retStr = str;
        try {
            retStr = str.substring(0, 1).toLowerCase() + str.substring(1);
        } catch (Exception ignored) { }
        return retStr;
    }

    public static String upperFirstChar(String str) {
        char[] array = str.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }

    public static int stringToInt(String str) {
        int id = 0;
        try {
            id = Integer.parseInt(str);
        } catch (Exception ignored) { }
        return id;
    }

    public static String niceDate(Date date) {
        return new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").format(date);
    }

    /* https://www.nbdtech.com/Blog/archive/2008/04/27/Calculating-the-Perceived-Brightness-of-a-Color.aspx */
    public static int brightness(Color c) {
        return (int) Math.sqrt(
            c.getRed() * c.getRed() * .241 +
            c.getGreen() * c.getGreen() * .691 +
            c.getBlue() * c.getBlue() * .068);
    }

}
