package com.traintracker.traintracker.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.data.user.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.traintracker.traintracker.manager.ManagerFragment.ARG_PAGE;

public class EditUserAdminFragment extends Fragment implements IEditUserAdminFragment {

    @BindView(R.id.add_user)
    Button add;
    @BindView(R.id.remove_all_user)
    Button remove_all;

    private ArrayList<User> users;
    private View view;
    private IEditUserAdminPresenter iEditUserAdminPresenter;
    private UsersAdapter adapter;
    private RecyclerView rvContacts;

    public static EditUserAdminFragment newInstance() {
        EditUserAdminFragment fragment = new EditUserAdminFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_user_admin, parent, false);
        return view;
    }

    public UsersAdapter getAdapter() {
        return adapter;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditUser();
            }
        });

        remove_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"TODO",Toast.LENGTH_LONG).show();
            }
        });

        iEditUserAdminPresenter = new EditUserAdminPresenter(this);
        rvContacts = (RecyclerView) view.findViewById(R.id.rvContacts);
        users = iEditUserAdminPresenter.getAllUsers();
        String i = Integer.toString(users.size());
        adapter = new UsersAdapter(getContext(), users, iEditUserAdminPresenter);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void showDialogEditUser(User user, int position) {
        Bundle args = new Bundle();
        args.putString("username", user.getUsername());
        args.putString("password", user.getPassword());
        args.putString("type", user.getType());
        FragmentManager fm = getFragmentManager();
        EditUserAdminDialogFragment editNameDialogFragment = EditUserAdminDialogFragment.newInstance("random");
        editNameDialogFragment.setArguments(args);
        editNameDialogFragment.setTargetFragment(EditUserAdminFragment.this, 300);
        editNameDialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                users = iEditUserAdminPresenter.getAllUsers();
                adapter = new UsersAdapter(getContext(), users, iEditUserAdminPresenter);
                rvContacts.setAdapter(adapter);
                rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        editNameDialogFragment.show(fm, "fragment_edit_user");
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void showDialogEditUser() {
        FragmentManager fm = getFragmentManager();
        AddUserAdminDialogFragment addUserAdminDialogFragment = AddUserAdminDialogFragment.newInstance(this);
        addUserAdminDialogFragment.show(fm, "fragment_add_user");
    }


}
