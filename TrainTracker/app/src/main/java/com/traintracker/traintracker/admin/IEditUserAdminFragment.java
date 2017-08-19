package com.traintracker.traintracker.admin;

import com.traintracker.traintracker.data.user.User;

import java.util.ArrayList;

/**
 * Created by ayoub on 15-Aug-17.
 */

public interface IEditUserAdminFragment {
    void showDialogEditUser(User user, int position);

    void notifyAdapter();

    ArrayList<User> getUsers();

    void setUsers(ArrayList<User> users);

    UsersAdapter getAdapter();

}
