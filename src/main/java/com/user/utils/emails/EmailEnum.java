package com.user.utils.emails;

public enum EmailEnum {
    ACCOUNT_CONFIRMATION("Action Required: Email Confirmation",
            "<p>For security reasons, you must confirm your email address before you continue. Thank you for" +
                    "your patience.</p><p>If you do not confirm your email address within 48 hours, your account will" +
                    "automatically be deleted.</p>",
            "Confirm Email"),
    DELETE_CONFIRMATION("Action Required: Account Deletion Confirmation",
            "<p>For security reasons, please confirm that you want to delete your account.</p>",
            "Confirm Deletion"),
    RESET_PASSWORD("Action Required: Change Password",
            "<p>For security reasons, you must change you password.</p><p>Do not reuse your passwords. Make" +
                    "sure your password does not sound right. Do it with uppercase letters, lowercase letters," +
                    "numbers, special characters. Make sure is more than 12 characters.</p>",
            "Update Password"),
    AUTH_CODE("Success: There Your 2-factor Login Code",
            "",
            ""),
    ;

    public final String subject;
    public final String body;
    public final String button;

    EmailEnum(String subject, String body, String button) {
        this.subject = subject;
        this.body = body;
        this.button = button;
    }

}
