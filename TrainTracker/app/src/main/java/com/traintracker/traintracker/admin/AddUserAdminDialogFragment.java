package com.traintracker.traintracker.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.data.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ayoub on 16-Aug-17.
 */

public class AddUserAdminDialogFragment extends android.support.v4.app.DialogFragment {
    private static IEditUserAdminFragment iEditUserAdminFragment;


    @BindView(R.id.edit_user_add)
    EditText username;
    @BindView(R.id.edit_password_add)
    EditText password;
    @BindView(R.id.edit_type_add)
    EditText type;

    public AddUserAdminDialogFragment() {
    }

    public static AddUserAdminDialogFragment newInstance(IEditUserAdminFragment iEditUserFragment) {
        AddUserAdminDialogFragment frag = new AddUserAdminDialogFragment();
        iEditUserAdminFragment = iEditUserFragment;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.cancel_new_user)
    public void OnCancel() {
        dismiss();
    }

    @OnClick(R.id.add_new_username)
    public void onSaveClick() {
        User user = new User(username.getText().toString(),
                type.getText().toString(), password.getText().toString());
        IEditUserAdminPresenter iEditUserAdminPresenter = new EditUserAdminPresenter(iEditUserAdminFragment);
        iEditUserAdminPresenter.addUser(user);
        iEditUserAdminFragment.getUsers().add(user);
        iEditUserAdminFragment.getAdapter().notifyDataSetChanged();
        dismiss();
    }


}
