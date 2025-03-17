package com.fintracker.in;

import com.fintracker.core.exception.ExitFromFinanceModuleException;
import com.fintracker.core.model.User;
import com.fintracker.core.model.UserRole;
import com.fintracker.core.service.AdminService;
import com.fintracker.core.service.TransactionService;
import com.fintracker.core.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс для обработки взаимодействия пользователя с системой через консоль.
 * <p>
 * Отвечает за регистрацию, вход в систему, управление аккаунтом, работу с финансами и администрирование.
 */
public class UserConsoleHandler {
    private final UserService userService;
    private User currentUser;
    private final TransactionService transactionService;
    private final TransactionConsoleHandler transactionConsoleHandler;
    private final Scanner scanner = new Scanner(System.in);
    private final AdminService adminService;
    private final Map<String, Runnable> guestCommands = new HashMap<>();
    private final Map<String, Runnable> authenticatedCommands = new HashMap<>();

    /**
     * Конструктор, инициализирующий обработчик команд пользователя с необходимыми сервисами.
     *
     * @param userService               сервис работы с пользователями
     * @param transactionService        сервис работы с транзакциями
     * @param transactionConsoleHandler консольный обработчик финансовых транзакций
     * @param adminService              сервис для администрирования пользователей и транзакций
     */
    public UserConsoleHandler(UserService userService, TransactionService transactionService,
                              TransactionConsoleHandler transactionConsoleHandler, AdminService adminService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.transactionConsoleHandler = transactionConsoleHandler;
        this.adminService = adminService;

        // Гостевые команды (регистрация, вход, выход)
        guestCommands.put("1", this::handleRegister);
        guestCommands.put("2", this::handleLogin);
        guestCommands.put("3", () -> {
            System.out.println("Выход из программы...");
            System.exit(0);
        });

        // Команды авторизованных пользователей
        authenticatedCommands.put("1", this::handleAccountMenu);
        authenticatedCommands.put("2", this::handleFinanceMenu);
        authenticatedCommands.put("3", this::handleLogout);
        authenticatedCommands.put("4", this::handleAdminPanel);
    }

    /**
     * Запускает основной цикл работы консольного интерфейса.
     */
    public void start() {
        while (true) {
            if (currentUser == null) {
                System.out.println("Введите команду (1 - Регистрация, 2 - Вход, 3 - Выход)");
                processCommands(guestCommands);
            } else {
                System.out.println("\nВыберите раздел:");
                System.out.println("1 - Аккаунт");
                System.out.println("2 - Финансы");
                System.out.println("3 - Выйти из аккаунта");
                if (currentUser.getRole() == UserRole.ADMIN) {
                    System.out.println("4 - Панель администратора");
                }

                processCommands(authenticatedCommands);
            }
        }
    }

    /**
     * Обрабатывает ввод команд пользователя.
     *
     * @param commands карта команд, соответствующих действиям пользователя
     */
    private void processCommands(Map<String, Runnable> commands) {
        String command = scanner.nextLine();
        commands.getOrDefault(command, () -> System.out.println("Неверная команда, попробуйте еще раз!")).run();
    }

    /**
     * Запускает модуль работы с финансами.
     * В случае выхода из него выбрасывает исключение {@link ExitFromFinanceModuleException}.
     */
    private void handleFinanceMenu() {
        try {
            transactionConsoleHandler.start(currentUser);
        } catch (ExitFromFinanceModuleException e) {
            System.out.println("Возвращаемся в главное меню...");
        }
    }

    /**
     * Открывает меню управления аккаунтом.
     */
    private void handleAccountMenu() {
        Map<String, Runnable> accountsCommand = new HashMap<>();
        accountsCommand.put("1", this::handleEdit);
        accountsCommand.put("2", this::handleDelete);
        accountsCommand.put("3", () -> {
        });

        while (currentUser != null) {
            System.out.println("\nАккаунт");
            System.out.println("1 - Редактировать профиль");
            System.out.println("2 - Удалить профиль");
            System.out.println("3 - Назад");

            String choice = scanner.nextLine();
            if (choice.equals("3")) return;

            accountsCommand.getOrDefault(choice, () -> System.out.println("Неверный ввод, повторите попытку")).run();
        }
    }

    /**
     * Регистрация нового пользователя.
     */
    private void handleRegister() {
        System.out.println("Введите Имя: ");
        String name = scanner.nextLine();
        System.out.println("Введите email: ");
        String email = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        User user = userService.register(new User(name, email, password, UserRole.USER));
        if (user != null) {
            System.out.println("Регистрация успешна!");
        } else {
            System.out.println("Ошибка: пользователь с таким email уже существует!");
        }
    }

    /**
     * Вход пользователя в систему.
     */
    private void handleLogin() {
        System.out.println("Введите email: ");
        String email = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();

        currentUser = userService.login(email, password);
        if (currentUser != null) {
            System.out.println("Успешный вход! Добро пожаловать, " + currentUser.getName());
        }
    }

    /**
     * Выход из учетной записи пользователя.
     */
    private void handleLogout() {
        if (currentUser != null) {
            System.out.println("Вы вышли из аккаунта: " + currentUser.getEmail());
            currentUser = null;
        } else {
            System.out.println("Вы не вошли в аккаунт");
        }
    }

    /**
     * Редактирование профиля пользователя.
     */
    private void handleEdit() {
        if (currentUser == null) {
            System.out.println("Сначала войдите в аккаунт");
            return;
        }
        System.out.println("Введите новое Имя (нажмите Enter, чтобы оставить текущее): ");
        String name = scanner.nextLine();
        System.out.println("Введите новый email (нажмите Enter, чтобы оставить текущее): ");
        String email = scanner.nextLine();
        System.out.println("Введите новый пароль (нажмите Enter, чтобы оставить текущее): ");
        String password = scanner.nextLine();

        if (name.isEmpty()) name = currentUser.getName();
        if (email.isEmpty()) email = currentUser.getEmail();
        if (password.isEmpty()) password = currentUser.getPassword();

        userService.editUser(currentUser.getEmail(), name, email, password);
        if (!email.equals(currentUser.getEmail())) {
            currentUser = userService.getUserByEmail(email);
        }

        System.out.println("Профиль пользователя успешно обновлен! Новые данные: имя - " + name + ", email - " + email + ", пароль - " + password);
    }

    /**
     * Удаление учетной записи пользователя.
     */
    private void handleDelete() {
        if (currentUser == null) {
            System.out.println("Сначала войдите в аккаунт!");
            return;
        }
        if (userService.deleteUser(currentUser.getEmail())) {
            System.out.println("Пользователь успешно удален! Возвращение в главное меню...");
            currentUser = null;
        } else {
            System.out.println("Ошибка удаления профиля");
        }
    }

    /**
     * Панель администратора для управления пользователями и транзакциями.
     */
    private void handleAdminPanel() {
        System.out.println("Вы в админ-панели.");
        System.out.println("1 - Посмотреть профили");
        System.out.println("2 - Удалить профили");
        System.out.println("3 - Посмотреть транзакции");
        System.out.println("4 - Выйти");

        String command = scanner.nextLine();
        if (command.equals("4")) return;

        handleViewUsers();
    }

    private void handleViewUsers() {
        adminService.getAllUser().forEach(System.out::println);
    }
}