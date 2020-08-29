package com.codrut.travelhelper.login;

import androidx.annotation.NonNull;

import com.codrut.travelhelper.BasePresenter;
import com.codrut.travelhelper.data.model.LoggedInUser;

public class LoginContract {
    public interface LoginPresenter extends BasePresenter<LoginView> {
        void loginWithEmail(@NonNull String email, @NonNull String password);

        void loginWithGoogle(String userIdToken);
    }

    public interface LoginView {
        void handleSuccessfulAuthentication(LoggedInUser loggedInUser);

        void showLoginError(Exception e);
    }
}
