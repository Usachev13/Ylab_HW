package com.fintracker.in;

import com.fintracker.core.models.Transaction;
import com.fintracker.core.models.User;
import com.fintracker.core.models.UserRole;
import com.fintracker.core.services.TransactionService;
import com.fintracker.core.services.UserService;

import java.util.Scanner;

public class UserConsoleHandler {
    private final UserService userService;
    private final TransactionService transactionService;

    public UserConsoleHandler(UserService userService,TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;

    }

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Введите команду (registration, login, edit, delete,admin_panel, exit) :");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "registration":
                    handleRegister();
                    break;
                case "login":
                    handleLogin();
                    break;
                case "edit":
                    handleEdit();
                    break;
                case "delete":
                    handleDelete();
                    break;
                case "admin_panel":
                    handleAdminPanel();
                    break;
                case "exit":
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Такая команда не найдена, попробуйте еще");
            }
        }
    }

    private void handleRegister() {
        System.out.println("Введите Имя : ");
        String name = scanner.nextLine();
        System.out.println("Введите email : ");
        String email = scanner.nextLine();
        System.out.println("Введите пароль : ");
        String password = scanner.nextLine();

        User user = userService.register(new User(name, email, password,UserRole.USER));
        if (user != null) {
            System.out.println("Регистрация успешна!");
        } else {
            System.out.println("Ошибка: пользователь с таким email уже существует!");
        }
    }

    private void handleLogin() {
        System.out.println("Введите email : ");
        String email = scanner.nextLine();
        System.out.println("Введите пароль : ");
        String password = scanner.nextLine();

        userService.login(email, password);

    }
    private void handleEdit(){
        System.out.println("Введите email пользователя, который хотите изменить: ");
        String oldEmail = scanner.nextLine();
        System.out.println("Введите новое Имя : ");
        String name = scanner.nextLine();
        System.out.println("Введите новый email : ");
        String email = scanner.nextLine();
        System.out.println("Введите новый пароль : ");
        String password = scanner.nextLine();

        User user = new User(name, email, password,UserRole.USER);
        userService.editUser(oldEmail,name,email,password);
        System.out.println("Профиль пользователя успешно обновлен!");
    }
    private void handleDelete(){
        System.out.println("Введите email пользователя, которого хотите удалить: ");
        String email = scanner.nextLine();
        if (userService.deleteUser(email)){
            System.out.println("Пользователь успешно удален!");
        }else {
            System.out.println("Такого пользователя не существует!");
        }
    }
    private void handleAdminPanel() {
        System.out.println("Введите email администратора:");
        String email = scanner.nextLine();
        User admin = userService.getUserByEmail(email);

        if (admin == null || admin.getRole() != UserRole.ADMIN) {
            System.out.println("Доступ запрещен! Только администратор может использовать эту команду.");
            return;
        }

        while (true) {
            System.out.println("Введите команду (view_users, delete_user, view_transactions, exit):");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "view_users":
                    handleViewUsers();
                    break;
                case "delete_user":
                    handleDeleteUser();
                    break;
                case "view_transactions":
                    handleViewTransactions();
                    break;
                case "exit":
                    System.out.println("Выход из админ-панели...");
                    return;
                default:
                    System.out.println("Команда не найдена. Попробуйте еще раз.");
            }
        }
    }
    private void handleViewUsers() {
        System.out.println("Список пользователей:");
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
    }
    private void handleDeleteUser() {
        System.out.println("Введите email пользователя, которого хотите удалить:");
        String email = scanner.nextLine();

        if (userService.deleteUserByEmail(email)) {
            System.out.println("Пользователь успешно удален!");
        } else {
            System.out.println("Пользователь не найден.");
        }
    }
    private void handleViewTransactions() {
        System.out.println("Список всех транзакций:");
        for (Transaction transaction : transactionService.listTransactions()) {
            System.out.println(transaction);
        }
    }
}
