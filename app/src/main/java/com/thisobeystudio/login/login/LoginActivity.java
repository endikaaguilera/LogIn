package com.thisobeystudio.login.login;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thisobeystudio.login.R;
import com.thisobeystudio.login.login.custom.CustomProgressDialog;
import com.thisobeystudio.login.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements
        LoginView,
//        View.OnKeyListener,
        View.OnClickListener {

    private LoginPresenter presenter;
    private ConstraintLayout parentContainer;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private CustomProgressDialog customProgressDialog;
    private Button signInButton;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this, new LoginInteraction());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        if (customProgressDialog != null) {
            customProgressDialog.hide();
            customProgressDialog = null;
        }
        super.onDestroy();
    }

    // region LoginView implementation

    @Override
    public void findViews() {
        parentContainer = findViewById(R.id.loginViewContainer);
        usernameEditText = findViewById(R.id.loginUsernameEditText);
        passwordEditText = findViewById(R.id.loginPasswordEditText);
        signInButton = findViewById(R.id.signInBtn);
        loginButton = findViewById(R.id.loginBtn);
    }

    @Override
    public void setElevations() {
        Resources resources = getResources();
        if (resources == null) return;
        int elevation = (int) resources.getDimension(R.dimen.progressDialogElevation);
        ViewCompat.setElevation(findViewById(R.id.logoImageView), elevation);
        ViewCompat.setElevation(usernameEditText, elevation);
        ViewCompat.setElevation(passwordEditText, elevation);
    }

    @Override
    public void setListeners() {
//        passwordEditText.setOnKeyListener(this);
        signInButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.validateUsername();
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.validatePassword();
            }
        });
    }

    @Override
    public void setSignInButtonEnabled(boolean enabled) {
        signInButton.setEnabled(enabled);
    }

    @Override
    public void setUsernameError(String error) {
        usernameEditText.setError(error);
        if (!usernameEditText.hasFocus()) usernameEditText.requestFocus();
    }

    @Override
    public void setPasswordError(String error) {
        passwordEditText.setError(error);
        if (!passwordEditText.hasFocus()) passwordEditText.requestFocus();
    }

    @Override
    public String getUsername() {
        CharSequence usernameChars = this.usernameEditText.getText();
        return usernameChars == null ? null : usernameChars.toString();
    }

    @Override
    public String getPassword() {
        CharSequence passwordChars = this.passwordEditText.getText();
        return passwordChars == null ? null : passwordChars.toString();
    }

    @Override
    public String getUsernameError() {
        CharSequence error = this.usernameEditText.getError();
        return error == null ? null : error.toString();
    }

    @Override
    public String getPasswordError() {
        CharSequence passwordChars = this.passwordEditText.getError();
        return passwordChars == null ? null : passwordChars.toString();
    }

    @Override
    public void showProgress() {
        if (customProgressDialog != null) return;
        customProgressDialog = new CustomProgressDialog(parentContainer);
        customProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (customProgressDialog == null) return;
        customProgressDialog.hide();
        customProgressDialog = null;
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    // endregion LoginView implementation

    // region onClick

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signInBtn) {
            if (usernameEditText != null && usernameEditText.hasFocus())
                usernameEditText.clearFocus();
            if (passwordEditText != null && passwordEditText.hasFocus())
                passwordEditText.clearFocus();

            presenter.validateCredentials();
        }
        if (view.getId() == R.id.loginBtn) {
            String text = "Not implemented yet...";
            Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    }

    // endregion onClick

    // region onKey

//    todo test this usernameEditText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
//    @Override
//    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//        if (keyEvent != null &&
//                keyCode == KeyEvent.KEYCODE_ENTER &&
//                view.getId() == R.id.loginPasswordEditText &&
//                keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//            if (usernameEditText != null && usernameEditText.hasFocus())
//                usernameEditText.clearFocus();
//            if (passwordEditText != null && passwordEditText.hasFocus())
//                passwordEditText.clearFocus();
//            presenter.validateCredentials();
//            return true;
//        }
//        return false;
//    }

    // endregion onKey

}
