package com.traintracker.traintracker.login;

import com.traintracker.traintracker.data.user.User;
import com.traintracker.traintracker.data.user.UserDataControl;

/**
 * Created by ayoub on 13-Aug-17.
 */

public class LoginPresenter implements ILoginPresenter {
    IUserLogin iUserLogin;

    public LoginPresenter() {
        iUserLogin = new UserDataControl();
    }

    public int isValid(String username, String password) {
        iUserLogin.setConnection();
        if (iUserLogin.isExist(username)) {
            User user = iUserLogin.getUser(username);
            if (user.getPassword().equals(password)) {

                if (user.getType().equals("admins")) {
                    iUserLogin.disconnectFromServer();
                    return 1;
                }

                if (user.getType().equals("users")) {
                    iUserLogin.disconnectFromServer();
                    return 2;
                }

                if (user.getType().equals("managers")) {
                    iUserLogin.disconnectFromServer();
                    return 3;
                }

                return 4;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

}
