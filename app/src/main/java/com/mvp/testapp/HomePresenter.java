package com.mvp.testapp;

import androidx.databinding.ObservableField;

public class HomePresenter implements HomeContract.Presenter {
    public ObservableField<String> email;
    public ObservableField<String> name;
    public ObservableField<String> imageUrl;
    public ObservableField<String> phone;
    private Contact mContact;
    private HomeContract.ViewModel mViewModel;

    public HomePresenter(HomeContract.ViewModel viewModel, Contact contact) {
        mViewModel = viewModel;
        mContact = contact;
        init();
    }

    private void init() {
        email = new ObservableField<>();
        name = new ObservableField<>();
        imageUrl = new ObservableField<>();
        phone = new ObservableField<>();
        email.set(mContact.getEmail());
        name.set(mContact.getName());
        phone.set(mContact.getMobile());
        imageUrl.set(mContact.getImageUrl());
    }

    @Override
    public void logout() {
        mViewModel.logout();
    }

    @Override
    public void editUser() {
        mViewModel.editUser(mContact);
    }
}
