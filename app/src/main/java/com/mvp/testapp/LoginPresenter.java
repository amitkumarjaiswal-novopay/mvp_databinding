package com.mvp.testapp;

import android.text.TextUtils;

import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

public class LoginPresenter implements LoginContract.Presenter {
    public ObservableField<String> email;
    public ObservableField<String> password;
    private LoginContract.ViewModel mViewModel;
    private List<Contact> allContacts;

    public LoginPresenter(LoginContract.ViewModel viewModel) {
        mViewModel = viewModel;
        initFields();
     //   allContacts = UserManagerUtils.getListContacts(App.self());
    }

    private void initFields() {
        email = new ObservableField<>();
        password = new ObservableField<>();
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(email.get())) {
            mViewModel.showLoginFailed("email error");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            mViewModel.showLoginFailed("password error");
            return false;
        }
        return true;
    }

    @Override
    public void doLogin() {
        if (isValidate()) {
            if (allContacts == null) allContacts = new ArrayList<>();
            Contact validContact = new Contact(email.get(), password.get());
            for (Contact contact : allContacts) {
                if (contact.getEmail().equals(validContact.getEmail()) && contact.getPassword()
                        .equals(validContact.getPassword())) {
                    mViewModel.login(contact);
                    return;
                }
            }
            mViewModel.showLoginFailed("Login Failed");
        }
    }

    @Override
    public void register() {
        mViewModel.register();
    }
}
