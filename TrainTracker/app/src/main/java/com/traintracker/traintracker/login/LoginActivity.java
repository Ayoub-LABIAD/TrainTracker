package com.traintracker.traintracker.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.admin.AdminActivity;
import com.traintracker.traintracker.manager.ManagerActivity;
import com.traintracker.traintracker.user.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    ILoginPresenter iLoginPresenter;
    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;
    @BindView(R.id.username)
    EditText user;
    @BindView(R.id.password)
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");
        iLoginPresenter = new LoginPresenter();
    }

    @OnClick(R.id.btn)
    public void onLoginClick(View view) {
        String username = usernameWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();

        if (username.isEmpty()) {
            usernameWrapper.setError("Not a valid username!");
            usernameWrapper.requestFocus();
            showKeyboard();
        } else if (password.isEmpty()) {
            passwordWrapper.setError("Not a valid password!");
            passwordWrapper.requestFocus();
            showKeyboard();
        } else {
            hideKeyboard();
            usernameWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);

            switch (iLoginPresenter.isValid(user.getText().toString(), pass.getText().toString())) {
                case 1:
                    Intent i = new Intent(this, AdminActivity.class);
                    startActivity(i);
                    break;
                case 2:
                    Intent j = new Intent(this, UserActivity.class);
                    startActivity(j);
                    break;
                case 3:
                    Intent k = new Intent(this, ManagerActivity.class);
                    startActivity(k);
                    break;
                case 0:
                    passwordWrapper.setError("This is an invalid password, please try again!");
                    passwordWrapper.requestFocus();
                    showKeyboard();
                    break;
                case -1:
                    usernameWrapper.setError("This username doesn't exist.");
                    usernameWrapper.requestFocus();
                    showKeyboard();
                    break;
                default:
                    break;
            }

        }
    }

    @OnClick(R.id.reset)
    public void onResetClick() {
        user.setText("");
        pass.setText("");
        usernameWrapper.requestFocus();
        showKeyboard();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        if (v != null)
            imm.showSoftInput(v, 0);
    }
}
