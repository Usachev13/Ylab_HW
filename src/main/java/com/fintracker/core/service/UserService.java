package com.fintracker.core.service;

import com.fintracker.core.model.User;
import com.fintracker.storage.UserStorage;

import java.util.List;

/**
 * Сервис для работы с пользователями, реализующий основную бизнес-логику.
 * Предоставляет функционал для регистрации, авторизации, изменения и удаления профилей пользователей.
 */
public class UserService {

    /** Хранилище пользователей. */
    private final UserStorage storage;

    /**
     * Конструктор, инициализирующий UserService с указанным хранилищем.
     *
     * @param storage хранилище пользователей
     */
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param user новый пользователь для регистрации
     * @return созданный пользователь или null, если email уже используется
     */
    public User register(User user) {
        if (storage.getUserByEmail(user.getEmail()) != null) {
            System.out.println("User уже есть в системе!");
            return null;
        } else {
            storage.addUser(user);
            return user;
        }
    }

    /**
     * Авторизация пользователя по email и паролю.
     *
     * @param email email пользователя
     * @param password пароль пользователя
     * @return авторизованный пользователь или null при неудаче
     */
    public User login(String email, String password) {
        User user = storage.getUserByEmail(email);
        if (user == null) {
            System.out.println("Пользователь не найден!");
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            System.out.println("Неверный пароль!");
            return null;
        }
    }

    /**
     * Редактирует данные существующего пользователя.
     *
     * @param email текущий email пользователя для поиска
     * @param newName новое имя пользователя (оставить пустым, чтобы не изменять)
     * @param newEmail новый email пользователя (оставить пустым, чтобы не изменять)
     * @param newPassword новый пароль пользователя (оставить пустым, чтобы не изменять)
     */
    public void editUser(String email, String newName, String newEmail, String newPassword) {
        User user = storage.getUserByEmail(email);
        if (user == null) {
            System.out.println("Пользователь не найден!");
            return;
        }
        if (!newName.isEmpty()) user.setName(newName);
        if (!newEmail.isEmpty()) user.setEmail(newEmail);
        if (!newPassword.isEmpty()) user.setPassword(newPassword);

        storage.updateUser(user);
        System.out.println("Профиль успешно обновлен!");
    }

    /**
     * Удаляет пользователя по email.
     *
     * @param email email пользователя для удаления
     * @return true, если пользователь был успешно удалён, иначе false
     */
    public boolean deleteUser(String email) {
        User user = storage.getUserByEmail(email);
        if (user == null) {
            System.out.println("Пользователь не найден!");
            return false;
        }
        storage.removeUser(email);
        return true;
    }

    /**
     * Возвращает список всех зарегистрированных пользователей.
     *
     * @return список пользователей
     */
    public List<User> getAllUsers() {
        return storage.getAllUsers();
    }

    /**
     * Удаляет пользователя по email, метод используется администраторами.
     *
     * @param email email пользователя
     * @return true, если пользователь был удалён, иначе false
     */
    public boolean deleteUserByEmail(String email) {
        User user = storage.getUserByEmail(email);
        if (user != null) {
            storage.removeUser(email);
            return true;
        }
        return false;
    }

    /**
     * Возвращает пользователя по его email.
     *
     * @param email email пользователя
     * @return найденный пользователь или null, если пользователь не найден
     */
    public User getUserByEmail(String email) {
        return storage.getUserByEmail(email);
    }
}
