package com.fintracker.storage;

import com.fintracker.core.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс UserStorage обеспечивает хранение и управление пользователями в памяти.
 * Хранение реализовано на основе HashMap, ключом является email пользователя.
 */
public class UserStorage {

    /** Map для хранения пользователей с email в качестве ключа. */
    private final Map<String, User> users = new HashMap<>();

    /**
     * Добавляет нового пользователя в хранилище.
     *
     * @param user пользователь, которого необходимо добавить
     * @return добавленный пользователь, либо null, если пользователь с таким email уже существует
     */
    public User addUser(User user) {
        if (users.containsKey(user.getEmail())) {
            return null;
        } else {
            users.put(user.getEmail(), user);
            return user;
        }
    }

    /**
     * Возвращает пользователя по email.
     *
     * @param email email пользователя для поиска
     * @return найденный пользователь, либо null, если пользователя с таким email нет
     */
    public User getUserByEmail(String email) {
        return users.get(email);
    }

    /**
     * Удаляет пользователя из хранилища по его email.
     *
     * @param email email пользователя для удаления
     * @return true, если пользователь был успешно удалён, иначе false
     */
    public boolean removeUser(String email) {
        return users.remove(email) != null;
    }

    /**
     * Возвращает список всех пользователей в хранилище.
     *
     * @return список пользователей
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Обновляет данные существующего пользователя.
     * Если пользователя нет в хранилище, он будет добавлен.
     *
     * @param user пользователь, данные которого нужно обновить
     */
    public void updateUser(User user) {
        users.put(user.getEmail(), user);
    }
}