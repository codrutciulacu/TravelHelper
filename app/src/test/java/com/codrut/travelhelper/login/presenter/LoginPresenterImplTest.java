package com.codrut.travelhelper.login.presenter;

import com.codrut.travelhelper.data.repository.LoginRepository;
import com.codrut.travelhelper.login.LoginContract;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GoogleAuthProvider.class})
public class LoginPresenterImplTest {

    @Mock
    private LoginRepository repository;
    @Mock
    private LoginContract.LoginView view;

    private LoginPresenterImpl presenter;

    @Before
    public void setUp() {
        presenter = new LoginPresenterImpl(repository);
        presenter.attachView(view);
    }

    @Test
    public void attachView_calls_setListener_of_repository() {
        presenter.attachView(view);

        verify(repository).setListener(any());
    }

    @Test
    public void loginWithGoogle() {
        setUpGoogleAuthProvider();

        presenter.loginWithGoogle(any());

        Mockito.verify(repository).loginWithGoogle(any());
    }

    @Test
    public void loginWithEmail_success() {
        presenter.loginWithEmail("test123@gmail.com", "123456");

        Mockito.verify(repository).loginWithEmail(anyString(), anyString());
    }

    @Test
    public void loginWithEmail_with_invalid_email() {
        presenter.loginWithEmail("", "");

        Mockito.verify(view).showLoginError(any());
    }

    @Test
    public void handleSuccessListener() {
        AuthResult result = mock(AuthResult.class);
        FirebaseUser user = mock(FirebaseUser.class);
        when(result.getUser()).thenReturn(user);

        presenter.onSuccess(result);

        verify(repository).setLoggedInUser(any());
        verify(view).handleSuccessfulAuthentication(any());
    }

    @Test
    public void handleFailureListener() {
        presenter.onFailure(any());

        verify(view).showLoginError(any());
    }

    private void setUpGoogleAuthProvider() {
        AuthCredential provider = mock(AuthCredential.class);
        PowerMockito.mockStatic(GoogleAuthProvider.class);
        PowerMockito.when(GoogleAuthProvider.getCredential(any(), any())).thenReturn(provider);
    }
}