package com.codrut.travelhelper.login.presenter;

import androidx.core.util.PatternsCompat;

public class EmailAndPasswordValidator {

    private LoginErrorHandler errorHandler;

    public boolean isValid(String email, String password) {
        return isUserNameValid(email) && isPasswordValid(password);
    }

    private boolean isUserNameValid(String email) {
        if (email.contains("@")) {
            return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
