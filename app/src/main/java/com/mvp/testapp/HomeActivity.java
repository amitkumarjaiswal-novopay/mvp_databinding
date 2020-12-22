package com.mvp.testapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mvp.databinding.demo.R;
import com.mvp.databinding.demo.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements HomeContract.ViewModel {
    private HomePresenter mPresenter;
    private ActivityHomeBinding mBinding;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getIntentExtras();
        mPresenter = new HomePresenter(this, mContact);
        mBinding.setPresenter(mPresenter);
    }

    private void getIntentExtras() {
        Intent intent = this.getIntent();
        mContact = (Contact) intent.getSerializableExtra(Constant.EXTRA_CONTACT);
    }

    @Override
    public void logout() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void editUser(Contact contact) {
        startActivity(new Intent(this, RegisterActivity.class).putExtra(Constant.EXTRA_CONTACT, contact));
        finish();
    }
}
