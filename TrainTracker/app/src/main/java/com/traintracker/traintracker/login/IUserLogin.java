package com.traintracker.traintracker.login;

import com.traintracker.traintracker.data.user.User;

/**
 * Created by ayoub on 13-Aug-17.
 */

public interface IUserLogin {
    void disconnectFromServer();

    boolean isExist(String username);

    User getUser(String username);

    void setConnection();
}
