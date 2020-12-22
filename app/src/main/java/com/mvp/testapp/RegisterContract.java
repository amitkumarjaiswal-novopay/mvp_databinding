package com.mvp.testapp;

public interface RegisterContract {
    interface ViewModel {
        void login(Contact contact);
        void showToast(String message);
        void pickImage();
    }
    interface Presenter {
        void doLogin();
        void filePicker();
    }
}
