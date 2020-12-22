package com.mvp.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mvp.databinding.demo.R;
import com.mvp.databinding.demo.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements LoginContract.ViewModel {
    private LoginPresenter mPresenter;
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mPresenter = new LoginPresenter(this);
        mBinding.setPresenter(mPresenter);
    }

    @Override
    public void login(Contact contact) {
        if (contact == null) return;
        startActivity(new Intent(this, HomeActivity.class).putExtra(Constant.EXTRA_CONTACT, contact));
        finish();
    }

    @Override
    public void showLoginFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}