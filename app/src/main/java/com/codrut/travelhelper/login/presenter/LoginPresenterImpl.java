package com.codrut.travelhelper.login.presenter;

import androidx.annotation.NonNull;

import com.codrut.travelhelper.data.OnResponseListener;
import com.codrut.travelhelper.data.model.LoggedInUser;
import com.codrut.travelhelper.data.repository.LoginRepository;
import com.codrut.travelhelper.login.LoginContract;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginPresenterImpl implements LoginContract.LoginPresenter, OnResponseListener<AuthResult> {
    private static LoginContract.LoginPresenter instance = null;

    private LoginContract.LoginView view;
    private LoginRepository repository;
    private EmailAndPasswordValidator validator;

    public LoginPresenterImpl(LoginRepository repository) {
        this.repository = repository;
        this.repository.setListener(this);

        this.validator = new EmailAndPasswordValidator();
    }

    public synchronized static LoginContract.LoginPresenter getInstance() {
        if (instance == null) {
            instance = new LoginPresenterImpl(LoginRepository.getInstance());
        }

        return instance;
    }

    @Override
    public void attachView(LoginContract.LoginView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        repository = null;
        view = null;
    }

    @Override
    public void loginWithEmail(@NonNull String email, @NonNull String password) {
        if (validator.isValid(email, password)) {
            repository.loginWithEmail(email, password);
        } else {
            //TODO: Show a custom error
            view.showLoginError(new RuntimeException("The email and password are not valid!"));
        }
    }

    @Override
    public void loginWithGoogle(String userIdToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(userIdToken, null);
        repository.loginWithGoogle(credential);
    }

    @Override
    public void onSuccess(AuthResult data) {
        LoggedInUser loggedInUser = getAuthResultAsLoggedInUser(data);

        repository.setLoggedInUser(loggedInUser);
        view.handleSuccessfulAuthentication(repository.getLoggedInUser());
    }

    private LoggedInUser getAuthResultAsLoggedInUser(AuthResult data) {
        FirebaseUser firebaseUser = Objects.requireNonNull(data.getUser());
        return new LoggedInUser(firebaseUser);
    }

    @Override
    public void onFailure(Exception e) {
        view.showLoginError(e);
    }
}
