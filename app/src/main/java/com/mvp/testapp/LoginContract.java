package com.mvp.testapp;

public interface LoginContract {

    interface ViewModel {
        void login(Contact contact);
        void showLoginFailed(String error);
        void register();
    }

    interface Presenter {
        void doLogin();
        void register();
    }
}
