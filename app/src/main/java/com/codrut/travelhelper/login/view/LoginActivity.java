package com.codrut.travelhelper.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codrut.travelhelper.R;
import com.codrut.travelhelper.data.model.LoggedInUser;
import com.codrut.travelhelper.login.LoginContract;
import com.codrut.travelhelper.login.presenter.LoginPresenterImpl;
import com.codrut.travelhelper.manage.view.ManageTripActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    public static final String ID = "id";
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    private LoginContract.LoginPresenter presenter;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initPresenter();
    }

    private void initView() {
        initFormFields();
        initSubmitButtons();
    }

    private void initPresenter() {
        presenter = LoginPresenterImpl.getInstance();
        presenter.attachView(this);
    }

    private void initFormFields() {
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.loginWithEmail(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });
    }

    private void initSubmitButtons() {
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button emailLoginButton = findViewById(R.id.submitButton);
        final SignInButton googleSignInButton = findViewById(R.id.googleSignInButton);

        emailLoginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            presenter.loginWithEmail(emailLoginButton.getText().toString(),
                    passwordEditText.getText().toString());
        });

        googleSignInButton.setOnClickListener(v -> loginWithGoogle(getGoogleSignInClient()));
    }

    private GoogleSignInClient getGoogleSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(this, gso);
    }

    private void loginWithGoogle(GoogleSignInClient client) {
        Intent intent = client.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    presenter.loginWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void handleSuccessfulAuthentication(LoggedInUser loggedInUser) {
        Log.d(TAG, loggedInUser.getUserId());
        Toast.makeText(getApplicationContext(), loggedInUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ManageTripActivity.class);
        intent.putExtra(ID, "Ia2Kqjsim2jNUuK9V8If");
        startActivity(intent);
    }

    @Override
    public void showLoginError(Exception e) {
        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}