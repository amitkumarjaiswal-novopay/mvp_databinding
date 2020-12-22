package com.mvp.testapp;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import java.util.List;

public class RegisterPresenter implements RegisterContract.Presenter {
    public ObservableField<String> username;
    public ObservableField<String> mobile;
    public ObservableField<String> email;
    public ObservableField<String> password;
    public ObservableField<String> imageUrl;
    public ObservableBoolean isImageSelected;
    public ObservableBoolean isNewOrEdit;
    private RegisterContract.ViewModel mViewModel;
    private List<Contact> mContactList;
    private Boolean isUpdate = false;
    private Contact mContact;

    public RegisterPresenter(RegisterContract.ViewModel viewModel, Contact contact) {
        mViewModel = viewModel;
        initFields();
        setValues(contact);
    }

    private void initFields() {
        username = new ObservableField<>();
        mobile = new ObservableField<>();
        email = new ObservableField<>();
        password = new ObservableField<>();
        imageUrl = new ObservableField<>();
        isImageSelected = new ObservableBoolean();
        isNewOrEdit = new ObservableBoolean();
        mContactList = ContactManagerUtil.getListContacts(App.self());
    }

    private void setValues(Contact contact) {
        if (contact == null) return;
        mContact = contact;
        username.set(contact.getName());
        mobile.set(contact.getMobile());
        email.set(contact.getEmail());
        if (TextUtils.isEmpty(contact.getImageUrl())) {
            isImageSelected.set(false);
        } else {
            imageUrl.set(contact.getImageUrl());
            isImageSelected.set(true);
        }
        isNewOrEdit.set(true);
        isUpdate = true;
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(username.get())) {
            mViewModel.showToast("enter username");
            return false;
        }
        if (TextUtils.isEmpty(mobile.get())) {
            mViewModel.showToast("enter mobile number");
            return false;
        }
        if (TextUtils.isEmpty(email.get())) {
            mViewModel.showToast("enter email");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            mViewModel.showToast("enter password");
            return false;
        }

        if (!isUpdate) {
            for (Contact contact : mContactList) {
                if (contact.getEmail().equals(email.get())) {
                    mViewModel.showToast("USer already exists");
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void doLogin() {
        if (isValidate()) {
            if (isUpdate) {
                Contact contact =
                        new Contact(mContact.getId(), username.get(), mobile.get(), email.get(),
                                imageUrl.get(), password.get());
                ContactManagerUtil.updateContact(App.self(), contact);
                mViewModel.login(contact);
                return;
            }
            Contact contact = new Contact(username.get(), mobile.get(), email.get(), imageUrl.get(),
                    password.get());
            ContactManagerUtil.saveContact(App.self(), contact);
            mViewModel.login(contact);
        }
    }

    @Override
    public void filePicker() {
        mViewModel.pickImage();
    }
}
