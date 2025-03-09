package com.fintracker.core.services;

import com.fintracker.core.models.User;
import com.fintracker.storage.UserStorage;

import java.util.List;

public class UserService {

    private final UserStorage storage;

    public UserService(UserStorage users){
        this.storage = users;
    }
    public User register(User user){
        if (storage.getUserByEmail(user.getEmail())!= null){
            System.out.println("User уже есть в системе!");
            return null;
        }else {
            storage.addUser(user);
            return user;
        }

    }
    public void login(String email, String password){
        User user = storage.getUserByEmail(email);
        if (user == null){
            System.out.println("Пользователь не найден!");
            return;
        }
        if (user.getPassword().equals(password)){
            System.out.println("Успешный вход!");
        }else {
            System.out.println("Неверный пароль!");
        }
    }
    public boolean editUser(String email, String newName, String newEmail, String newPassword) {
        User user = storage.getUserByEmail(email);
        if (user == null) {
            System.out.println("Пользователь не найден!");
            return false;
        }
        if (!newName.isEmpty()) user.setName(newName);
        if (!newEmail.isEmpty()) user.setEmail(newEmail);
        if (!newPassword.isEmpty()) user.setPassword(newPassword);
        System.out.println("Профиль успешно обновлен!");
        return true;
    }

    public boolean deleteUser(String email) {
        User user = storage.getUserByEmail(email);
        if (user == null) {
            System.out.println("Пользователь не найден!");
            return false;
        }
        storage.removeUser(email);
        System.out.println("Аккаунт успешно удален.");
        return true;
    }
    public List<User> getAllUsers() {
        return storage.getAllUsers();
    }
    public boolean deleteUserByEmail(String email) {
        User user = storage.getUserByEmail(email);
        if (user != null) {
            storage.removeUser(email);
            return true;
        }
        return false;
    }
    public User getUserByEmail(String email) {
        return storage.getUserByEmail(email);
    }

}
