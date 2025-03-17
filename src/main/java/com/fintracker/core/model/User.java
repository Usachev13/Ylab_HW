package com.fintracker.core.model;

import java.util.UUID;

/**
 * Представляет пользователя системы финансового трекера.
 */
public class User {
    /**
     * Уникальный идентификатор пользователя.
     */
    private String id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Email пользователя, используется как уникальный идентификатор для входа.
     */
    private String email;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Роль пользователя в системе (USER, ADMIN).
     */
    private UserRole role;

    /**
     * Конструктор создания нового пользователя.
     *
     * @param name     имя пользователя.
     * @param email    email пользователя.
     * @param password пароль пользователя.
     * @param role     роль пользователя.
     */
    public User(String name, String email, String password, UserRole role) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return идентификатор пользователя.
     */
    public String getId() {
        return id;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает email пользователя.
     *
     * @return email пользователя.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает роль пользователя.
     *
     * @return роль пользователя.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Устанавливает новое имя пользователя.
     *
     * @param name новое имя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Устанавливает новый email пользователя.
     *
     * @param email новый email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Устанавливает новый пароль пользователя.
     *
     * @param password новый пароль.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Устанавливает новую роль пользователя.
     *
     * @param role новая роль.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Возвращает строковое представление объекта User.
     *
     * @return строковое представление.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}