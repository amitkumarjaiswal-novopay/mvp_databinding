package com.mvp.testapp;

public interface HomeContract {
    interface ViewModel {
        void logout();
        void editUser(Contact contact);
    }
    interface Presenter {
        void logout();
        void editUser();
    }
}