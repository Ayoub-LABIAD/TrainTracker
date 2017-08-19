package com.traintracker.traintracker.admin;

import com.traintracker.traintracker.data.user.User;

import java.util.ArrayList;

/**
 * Created by ayoub on 15-Aug-17.
 */

public interface IEditUserAdminPresenter {
    void editUser(User user, int position);

    ArrayList<User> getAllUsers();

    int modifyUser(User oldUser, User newUser);

    void removeUser(User user);

    void addUser(User user);
}
