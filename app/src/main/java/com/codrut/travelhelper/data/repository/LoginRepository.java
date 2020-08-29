package com.codrut.travelhelper.data.repository;

import com.codrut.travelhelper.data.OnResponseListener;
import com.codrut.travelhelper.data.model.LoggedInUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Class that requests authentication and user information from the firebase
 */
public class LoginRepository {
    private static LoginRepository instance = null;

    private FirebaseAuth firebaseAuth;
    private OnResponseListener<AuthResult> listener;
    private LoggedInUser loggedInUser;

    LoginRepository(final FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository(FirebaseAuth.getInstance());
        }
        return instance;
    }

    public OnResponseListener<AuthResult> getListener() {
        return listener;
    }

    public void setListener(OnResponseListener<AuthResult> listener) {
        this.listener = listener;
    }

    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public void loginWithEmail(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(listener::onFailure);
    }

    public void loginWithGoogle(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(listener::onFailure);
    }
}