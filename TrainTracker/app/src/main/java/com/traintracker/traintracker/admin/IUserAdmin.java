package com.traintracker.traintracker.admin;

import com.traintracker.traintracker.data.user.User;

import java.util.ArrayList;

/**
 * Created by ayoub on 15-Aug-17.
 */

public interface IUserAdmin {
    void setConnection();

    void disconnectFromServer();

    void moveUser(User user, String newType);

    void addUser(User user);

    boolean removeUser(String username);

    ArrayList<User> initArray();

    void changePassword(User user, String password);

    void changeUsername(User user, String username);

    boolean isExist(String username);


}
