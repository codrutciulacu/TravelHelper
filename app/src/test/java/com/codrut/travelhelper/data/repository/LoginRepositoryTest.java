package com.codrut.travelhelper.data.repository;

import android.app.Activity;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codrut.travelhelper.data.OnResponseListener;
import com.codrut.travelhelper.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Objects;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginRepositoryTest {

    private LoginRepository repository;
    @Mock
    private FirebaseAuth auth;
    @Mock
    private FirebaseUser user;
    @Mock
    private OnResponseListener<AuthResult> listener;

    @Before
    public void setUp() {
        repository = new LoginRepository(auth);
        repository.setListener(listener);
    }

    @Test
    public void setListener_and_getListener_work() {
        assertNotNull(repository.getListener());
    }

    @Test
    public void isLoggedIn_is_true_if_a_user_is_logged_in() {
        when(auth.getCurrentUser()).thenReturn(user);

        assertTrue(repository.isLoggedIn());
    }

    @Test
    public void isLoggedIn_is_false_if_a_user_is_not_logged_in() {
        when(auth.getCurrentUser()).thenReturn(null);

        assertFalse(repository.isLoggedIn());
    }

    @Test
    public void getLoggedInUser_returns_the_user_if_a_user_is_logged_in() {
        LoggedInUser user = new LoggedInUser("mockId", "mockName");
        repository.setLoggedInUser(user);

        assertEquals(user, repository.getLoggedInUser());
    }

    @Test
    public void logout() {
        repository.logout();

        verify(auth).signOut();
    }

    @Test
    public void loginWithEmail_success() {
        when(auth.signInWithEmailAndPassword(any(), any())).thenReturn(new SuccessAuthTask(user));

        repository.loginWithEmail(anyString(), anyString());

        verify(listener).onSuccess(any());
    }

    @Test
    public void loginWithEmail_failure() {
        when(auth.signInWithEmailAndPassword(any(), any())).thenReturn(new FailureAuthTask());

        repository.loginWithEmail(anyString(), anyString());

        verify(listener).onFailure(any());
    }

    @Test
    public void loginWithGoogle_success() {
        when(auth.signInWithCredential(any())).thenReturn(new SuccessAuthTask(user));

        repository.loginWithGoogle(any());

        verify(listener).onSuccess(any());
    }

    @Test
    public void loginWithGoogle_failure() {
        when(auth.signInWithCredential(any())).thenReturn(new FailureAuthTask());

        repository.loginWithGoogle(any());

        verify(listener).onFailure(any());
    }
}

class SuccessAuthTask extends Task<AuthResult> {

    private FirebaseUser user;

    public SuccessAuthTask(FirebaseUser user) {
        this.user = user;
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return true;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Nullable
    @Override
    public AuthResult getResult() {
        return new AuthResult() {
            @Nullable
            @Override
            public FirebaseUser getUser() {
                return user;
            }

            @Nullable
            @Override
            public AdditionalUserInfo getAdditionalUserInfo() {
                return null;
            }

            @Nullable
            @Override
            public AuthCredential getCredential() {
                return null;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

            }
        };
    }

    @Nullable
    @Override
    public <X extends Throwable> AuthResult getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
        onSuccessListener.onSuccess(getResult());
        return this;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }
}

class FailureAuthTask extends Task<AuthResult> {

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Nullable
    @Override
    public AuthResult getResult() {
        return null;
    }

    @Nullable
    @Override
    public <X extends Throwable> AuthResult getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return new RuntimeException("An error with firebase has occurred!");
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
        return this;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        Exception exception = Objects.requireNonNull(getException());

        onFailureListener.onFailure(exception);

        return this;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }
}