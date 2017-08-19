package com.traintracker.traintracker.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.data.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ayoub on 15-Aug-17.
 */

public class EditUserAdminDialogFragment extends DialogFragment {

    @BindView(R.id.edit_user)
    EditText username;
    @BindView(R.id.edit_password)
    EditText password;
    @BindView(R.id.edit_type)
    EditText type;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.cancel)
    Button cancel;
    private User oldUser, newUser;
    private IEditUserAdminPresenter iEditUserAdminPresenter;
    private DialogInterface.OnDismissListener onDismissListener;

    public EditUserAdminDialogFragment() {
    }

    public static EditUserAdminDialogFragment newInstance(String title) {
        EditUserAdminDialogFragment frag = new EditUserAdminDialogFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_user, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle mArgs = getArguments();
        String mUsername = mArgs.getString("username");
        String mPassword = mArgs.getString("password");
        String mType = mArgs.getString("type");
        oldUser = new User(mUsername, mType, mPassword);
        username.setText(mUsername);
        type.setText(mType);
        password.setText(mPassword);
    }

    @OnClick(R.id.cancel)
    public void OnCancel() {
        dismiss();
    }

    @OnClick(R.id.save)
    public void onSaveClick() {
        newUser = new User(username.getText().toString(), type.getText().toString(), password.getText().toString());
        iEditUserAdminPresenter = new EditUserAdminPresenter();
        int i = iEditUserAdminPresenter.modifyUser(oldUser, newUser);
        switch (i) {
            case -5:
                username.setError("User name invalid. Please try another one.");
                break;
            case -4:
                password.setError("Password invalid. Please try again.");
                break;
            case -3:
                type.setError("Type invalid. Please check it!");
                break;
            case 1:
                Toast.makeText(getContext(), "The user has been successfully updated.", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
