package com.traintracker.traintracker.admin;

import com.traintracker.traintracker.data.user.User;
import com.traintracker.traintracker.data.user.UserDataControl;

import java.util.ArrayList;

public class EditUserAdminPresenter implements IEditUserAdminPresenter {
    IUserAdmin iUserAdmin;
    IEditUserAdminFragment iEditUserAdminFragment;

    public EditUserAdminPresenter(IEditUserAdminFragment iEditUserAdminFragment) {
        iUserAdmin = new UserDataControl();
        this.iEditUserAdminFragment = iEditUserAdminFragment;
    }

    public EditUserAdminPresenter() {
        iUserAdmin = new UserDataControl();
    }

    public void editUser(User user, int position) {
        iEditUserAdminFragment.showDialogEditUser(user, position);

    }

    public void addUser(User user) {
        iUserAdmin.setConnection();
        iUserAdmin.addUser(user);
        iUserAdmin.disconnectFromServer();
    }

    public ArrayList<User> getAllUsers() {
        iUserAdmin.setConnection();
        ArrayList<User> tmp = iUserAdmin.initArray();
        iUserAdmin.disconnectFromServer();
        return tmp;
    }

    public void removeUser(User user) {
        iUserAdmin.setConnection();
        iUserAdmin.removeUser(user.getUsername());
        iEditUserAdminFragment.getUsers().remove(user);
        iEditUserAdminFragment.notifyAdapter();
        iUserAdmin.disconnectFromServer();
    }

    public int modifyUser(User oldUser, User newUser) {
        iUserAdmin.setConnection();
        if (newUser.getUsername().equals(oldUser.getUsername())) {
            if (!newUser.getPassword().isEmpty()) {
                if (!newUser.getType().isEmpty()) {
                    iUserAdmin.removeUser(oldUser.getUsername());
                    iUserAdmin.addUser(newUser);
                    iUserAdmin.disconnectFromServer();
                    return 1;
                } else {
                    return -3;
                }
            } else {
                return -4;
            }
        } else {
            if (!newUser.getUsername().isEmpty() && !iUserAdmin.isExist(newUser.getUsername())) {
                if (!newUser.getPassword().isEmpty()) {
                    if (!newUser.getType().isEmpty()) {
                        iUserAdmin.removeUser(oldUser.getUsername());
                        iUserAdmin.addUser(newUser);
                        iUserAdmin.disconnectFromServer();
                        return 1;
                    } else {
                        return -3;
                    }
                } else {
                    return -4;
                }
            } else {
                return -5;
            }

        }
    }

}
