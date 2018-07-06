package com.thisobeystudio.login.login;

interface LoginView {

    void findViews();

    void setElevations();

    void setListeners();

    void setSignInButtonEnabled(boolean enabled);

    void setUsernameError(String error);

    void setPasswordError(String error);

    String getUsername();

    String getPassword();

    String getUsernameError();

    String getPasswordError();

    void showProgress();

    void hideProgress();

    void navigateToMain();

}
