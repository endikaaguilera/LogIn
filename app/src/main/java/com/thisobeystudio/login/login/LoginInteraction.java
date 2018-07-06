package com.thisobeystudio.login.login;

import android.os.Handler;

class LoginInteraction {

    private static final String VALIDATE_USERNAME_ERROR = "❌ Unable to validate user. Try 'admin'";
    private static final String VALIDATE_PASSWORD_ERROR = "❌ Incorrect password. Try 'HelloWorld'";

    private static final String ERROR_EMPTY_USERNAME = "❌ Username can not be empty\n";
    private static final String ERROR_EMPTY_PASSWORD = "❌ Password can not be empty\n";

    private static final int MIN_USERNAME_LENGTH = 2;
    private static final int MIN_PASSWORD_LENGTH = 8;

    private static final String ERROR_USERNAME_LENGTH =
            "❌ Min username length is " + MIN_USERNAME_LENGTH + "\n";
    private static final String ERROR_PASSWORD_LENGTH =
            "❌ Min password length is " + MIN_PASSWORD_LENGTH + "\n";

    interface OnLoginFinishedListener {

        void onUsernameError(String error);

        void onPasswordError(String error);

        void onSuccess();
    }

    public String getUsernameError(final CharSequence username) {
        String msg = "";
        if (username == null || username.length() == 0) msg += ERROR_EMPTY_USERNAME;
        if (username == null || username.length() < MIN_USERNAME_LENGTH)
            msg += ERROR_USERNAME_LENGTH;
        return msg.equals("") ? null : msg.trim();
    }

    public String getPasswordError(final CharSequence password) {
        String msg = "";
        if (password == null || password.length() == 0) msg += ERROR_EMPTY_PASSWORD;
        if (password == null || password.length() < MIN_PASSWORD_LENGTH)
            msg += ERROR_PASSWORD_LENGTH;
        return msg.equals("") ? null : msg.trim();
    }

    // Mock credentials
    private final static String MOCK_USERNAME = "admin";
    private final static String MOCK_PASSWORD = "HelloWorld";

    public void login(final String username,
                      final String password,
                      final OnLoginFinishedListener listener) {

        // Mock login
        // Create a handler to delay the answer
        final int delay = 2000; // 2 secs
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        String usernameError = getUsernameError(username);
                        if (usernameError != null && usernameError.length() > 0) {
                            listener.onUsernameError(usernameError);
                            return;
                        }

                        String passwordError = getPasswordError(password);
                        if (passwordError != null && passwordError.length() > 0) {
                            listener.onPasswordError(passwordError);
                            return;
                        }

                        if (!username.equals(MOCK_USERNAME)) {
                            listener.onUsernameError(VALIDATE_USERNAME_ERROR);
                            return;
                        }

                        if (!password.equals(MOCK_PASSWORD)) {
                            listener.onPasswordError(VALIDATE_PASSWORD_ERROR);
                            return;
                        }

                        listener.onSuccess();
                    }

                }, delay);
    }

}
