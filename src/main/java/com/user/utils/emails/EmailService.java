package com.user.utils.emails;

import com.user.Environment;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.HashMap;
import java.util.Properties;

// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class EmailService implements Runnable {

    Session session;
    final HashMap<String, String> styleHashMap = new HashMap<String, String>();

    EmailEnum emailEnum;
    String to;
    String username;
    String token;

    public EmailService(EmailEnum emailEnum, String to, String username, String token) {
        this.init();
        this.emailEnum = emailEnum;
        this.to = to;
        this.username = username;
        this.token = token;
        Thread t = new Thread(this);
        t.start();
    }

    private void init() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Environment.getInstance().EMAIL_USERNAME, Environment.getInstance().EMAIL_PASSWORD);
                    }
                });

        styleHashMap.put("h3", "font-size:24px;letter-spacing:3px;padding-bottom:10px;text-align:center;color:#000;font-weight:400");
        styleHashMap.put("h4", "letter-spacing:3px;font-size:20px;text-align:center;color:#000;font-weight:400");
        styleHashMap.put("h2", "letter-spacing:2px;padding-bottom:10px;font-size:18px;text-align:center;color:#000;font-weight:400");
        styleHashMap.put("footer", "text-align:center;line-height:150%;padding-top:25px;font-size:12px");
        styleHashMap.put("container", "font-size:14px;margin:auto;text-align:center");
        styleHashMap.put("meg", "margin:auto;max-width:680px;padding:35px 15px 10px;background-color:#fff");
        styleHashMap.put("li", "display:inline-block;font-size:40px;padding:15px 30px;margin:10px;border:2px solid grey");
        styleHashMap.put("ul", "padding-right:15px");
        styleHashMap.put("warning", "color:#de0012!important;");
        styleHashMap.put("button", "background-color:#28a5df;border:.1rem solid #28a5df;cursor:pointer;display:inline-block;color:#fff;padding:10px 50px;margin:10px 0;text-align:center;text-transform:uppercase;letter-spacing:1.5px;font-size:12px");
        styleHashMap.put("a", "color:#28a5df");
        styleHashMap.put("rescue_link", "color:#bcbcbc;font-size:14px");
        styleHashMap.put("display", "background:rgba(0,0,0,.1);padding:10px 16px;text-align:left!important");
    }

    @Override
    public void run() {

    }

}
