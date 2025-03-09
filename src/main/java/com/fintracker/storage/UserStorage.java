package com.fintracker.storage;

import com.fintracker.core.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStorage {
    private final Map<String, User> users = new HashMap<>();

    public User addUser(User user){
        if (users.containsKey(user.getEmail())){
            return null;
        }else {
            users.put(user.getEmail(), user);
            return user;
        }
    }
    public User getUserByEmail(String email){
        return users.get(email);
    }
    public boolean removeUser(String email){
        return users.remove(email) !=null;
    }
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

}
