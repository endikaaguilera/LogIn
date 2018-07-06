package com.thisobeystudio.login.login;

public class LoginPresenter implements LoginInteraction.OnLoginFinishedListener {

    private LoginView loginView;
    private final LoginInteraction loginInteraction;

    LoginPresenter(LoginView loginView, LoginInteraction loginInteraction) {
        this.loginView = loginView;
        this.loginInteraction = loginInteraction;
        if (this.loginView == null || this.loginInteraction == null) return;
        this.loginView.findViews();
        this.loginView.setElevations();
        this.loginView.setListeners();
        this.loginView.setSignInButtonEnabled(false);
    }

    private boolean setSignInButtonEnabled() {
        if (this.loginView == null) return false;
        String username = loginView.getUsername();
        String usernameError = loginView.getUsernameError();
        String password = loginView.getPassword();
        String passwordError = loginView.getPasswordError();
        return (username != null && username.length() > 0) &&
                (password != null && password.length() > 0) &&
                (passwordError == null || passwordError.length() == 0) &&
                (usernameError == null || usernameError.length() == 0);
    }

    public void validateUsername() {
        if (this.loginView == null || this.loginInteraction == null) return;
        String username = this.loginView.getUsername();
        String error = this.loginInteraction.getUsernameError(username);
        this.loginView.setUsernameError(error);
        this.loginView.setSignInButtonEnabled(setSignInButtonEnabled());
    }

    public void validatePassword() {
        if (this.loginView == null || this.loginInteraction == null) return;
        String password = this.loginView.getPassword();
        String error = this.loginInteraction.getPasswordError(password);
        this.loginView.setPasswordError(error);
        this.loginView.setSignInButtonEnabled(setSignInButtonEnabled());
    }

    public void validateCredentials() {
        if (this.loginView == null || this.loginInteraction == null) return;
        this.loginView.showProgress();
        String username = this.loginView.getUsername();
        String password = this.loginView.getPassword();
        this.loginView.setSignInButtonEnabled(false);
        this.loginInteraction.login(username, password, this);
    }

    public void onDestroy() {
        if (this.loginView != null) this.loginView = null;
    }

    @Override
    public void onUsernameError(String error) {
        if (this.loginView == null) return;
        this.loginView.setUsernameError(error);
        this.loginView.hideProgress();
    }

    @Override
    public void onPasswordError(String error) {
        if (this.loginView == null) return;
        this.loginView.setPasswordError(error);
        this.loginView.hideProgress();
    }

    @Override
    public void onSuccess() {
        if (this.loginView != null) this.loginView.navigateToMain();
    }
}