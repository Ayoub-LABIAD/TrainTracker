package com.traintracker.traintracker.data.user;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.traintracker.traintracker.admin.IUserAdmin;
import com.traintracker.traintracker.data.train.Train;
import com.traintracker.traintracker.login.IUserLogin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by ayoub on 13-Aug-17.
 */

public class UserDataControl implements IUserLogin, IUserAdmin {

    public static final String TYPE_USER = "users";
    public static final String TYPE_ADMIN = "admins";
    public static final String TYPE_MANAGER = "managers";
    private static final String HOST = "192.168.201.1";
    private static final String PORT = "6379";
    private static final String USER_DB = "0";
    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> syncCommands;
    private Map<String, User> USERS = null;

    public void setConnection() {
        StringBuilder sb = new StringBuilder();
        sb.append("redis://").append(HOST).append(":").append(PORT).append("/").append(USER_DB);
        redisClient = RedisClient.create(sb.toString());
        connection = redisClient.connect();
        syncCommands = connection.sync();
    }

    public void disconnectFromServer() {
        connection.close();
    }

    public boolean isExist(User user) {
        if (syncCommands.hexists(user.getType(), user.getUsername()))
            return true;
        return false;
    }

    public void moveUser(User user, String newType) {
        removeUser(user);
        user.setType(newType);
        addUser(user);
    }

    public void changePassword(User user, String password) {
        syncCommands.hset(user.getType(), user.getUsername(), password);
    }

    public void changeUsername(User user, String username) {
        removeUser(user);
        syncCommands.hset(user.getType(), username, user.getPassword());
    }

    public boolean isExist(String username) {
        if (syncCommands.hexists(TYPE_USER, username)) {
            return true;
        } else if (syncCommands.hexists(TYPE_MANAGER, username)) {
            return true;
        } else if (syncCommands.hexists(TYPE_ADMIN, username)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String username) {
        if (syncCommands.hexists(TYPE_USER, username)) {
            return new User(username, TYPE_USER, syncCommands.hget(TYPE_USER, username));
        } else if (syncCommands.hexists(TYPE_MANAGER, username)) {
            return new User(username, TYPE_MANAGER, syncCommands.hget(TYPE_MANAGER, username));
        } else if (syncCommands.hexists(TYPE_ADMIN, username)) {
            return new User(username, TYPE_ADMIN, syncCommands.hget(TYPE_ADMIN, username));
        } else {
            return null;
        }
    }

    private Map<String, User> init() {
        Map<String, String> users;
        users = syncCommands.hgetall(TYPE_USER);
        for (Map.Entry<String, String> tmpUser : users.entrySet()) {
            USERS.put(tmpUser.getKey(), new User(tmpUser.getKey(), TYPE_USER, tmpUser.getValue()));
        }
        users = syncCommands.hgetall(TYPE_MANAGER);
        for (Map.Entry<String, String> tmpUser : users.entrySet()) {
            USERS.put(tmpUser.getKey(), new User(tmpUser.getKey(), TYPE_MANAGER, tmpUser.getValue()));
        }
        users = syncCommands.hgetall(TYPE_ADMIN);
        for (Map.Entry<String, String> tmpUser : users.entrySet()) {
            USERS.put(tmpUser.getKey(), new User(tmpUser.getKey(), TYPE_ADMIN, tmpUser.getValue()));
        }
        return USERS;
    }

    private static Comparator<User> ALPHA_ORDER = new Comparator<User>() {
        public int compare(User user1, User user2) {
            int x = String.CASE_INSENSITIVE_ORDER.compare(user1.getUsername(), user2.getUsername());
            if (x== 0) {
                x= user1.getUsername().compareTo(user2.getUsername());
            }
            return x;
        }
    };

    public ArrayList<User> initArray() {
        ArrayList<User> allUsers = new ArrayList<>();
        Map<String, String> users;
        users = syncCommands.hgetall(TYPE_USER);

        for (Map.Entry<String, String> tmpUser : users.entrySet()) {
            allUsers.add(new User(tmpUser.getKey(), TYPE_USER, tmpUser.getValue()));
        }
        users = syncCommands.hgetall(TYPE_MANAGER);
        for (Map.Entry<String, String> tmpUser : users.entrySet()) {
            allUsers.add(new User(tmpUser.getKey(), TYPE_MANAGER, tmpUser.getValue()));
        }
        users = syncCommands.hgetall(TYPE_ADMIN);
        for (Map.Entry<String, String> tmpUser : users.entrySet()) {
            allUsers.add(new User(tmpUser.getKey(), TYPE_ADMIN, tmpUser.getValue()));
        }
        Collections.sort(allUsers,ALPHA_ORDER);
        return allUsers;
    }

    public void addUser(User user) {
        syncCommands.hset(user.getType(), user.getUsername(), user.getPassword());
    }

    public void removeUser(User user) {
        syncCommands.hdel(user.getType(), user.getUsername());
    }


    public boolean removeUser(String username) {
        if (syncCommands.hdel(TYPE_USER, username) > 1) {
            return true;
        } else {
            if (syncCommands.hdel(TYPE_MANAGER, username) > 1) {
                return true;
            } else {
                if (syncCommands.hdel(TYPE_ADMIN, username) > 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

}
