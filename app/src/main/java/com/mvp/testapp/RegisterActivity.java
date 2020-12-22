package com.mvp.testapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mvp.databinding.demo.R;
import com.mvp.databinding.demo.databinding.ActivityRegisterBinding;


public class RegisterActivity extends AppCompatActivity implements RegisterContract.ViewModel {
    private final static int MEDIA_LIBRARY = 1;
    private final int STORAGE_PERMISSION_ID = 0;
    private RegisterPresenter mPresenter;
    private ActivityRegisterBinding mBinding;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        getData();
        mPresenter = new RegisterPresenter(this, mContact);
        mBinding.setPresenter(mPresenter);
        mPresenter.isNewOrEdit.set(true);
    }

    private void getData() {
        Intent intent = this.getIntent();
        if (intent==null)return;
        mContact = (Contact) intent.getSerializableExtra(Constant.EXTRA_CONTACT);
    }

    @Override
    public void login(Contact contact) {
        if (contact == null) return;
        startActivity(
                new Intent(this, HomeActivity.class).putExtra(Constant.EXTRA_CONTACT, contact));
        finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pickImage() {
        selectImage();
    }

    private void selectImage() {
        Intent intent;
        if (checkStorePermission(STORAGE_PERMISSION_ID)) {
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] { "image/*" });
            }
            startActivityForResult(intent, MEDIA_LIBRARY);
        } else {
            showRequestPermission(STORAGE_PERMISSION_ID);
        }
    }

    private boolean checkStorePermission(int permission) {
        if (permission == STORAGE_PERMISSION_ID) {
            return PermissionsUtil.checkPermissions(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            return true;
        }
    }

    private void showRequestPermission(int requestCode) {
        String[] permissions;
        if (requestCode == STORAGE_PERMISSION_ID) {
            permissions = new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        } else {
            permissions = new String[] {
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        }
        PermissionsUtil.requestPermissions(this, requestCode, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        try {
            if (data.getData() == null) {
                showToast("error in image");
                return;
            }
            Uri uri = data.getData();
            String path = ImageFilePath.getPath(this, uri);
            mPresenter.imageUrl.set(path);
            mPresenter.isImageSelected.set(true);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
