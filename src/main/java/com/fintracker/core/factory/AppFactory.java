package com.fintracker.core.factory;

import com.fintracker.core.service.*;
import com.fintracker.in.TransactionConsoleHandler;
import com.fintracker.in.UserConsoleHandler;
import com.fintracker.out.AnalyticsService;
import com.fintracker.out.NotificationService;
import com.fintracker.storage.TransactionStorage;
import com.fintracker.storage.UserStorage;

/**
 * Фабрика для создания и предоставления экземпляров сервисов и обработчиков приложения.
 * Использует паттерн Singleton для всех компонентов.
 */
public class AppFactory {

    /**
     * Хранилище пользователей
     */
    private final static UserStorage userStorage = new UserStorage();

    /**
     * Хранилище транзакций
     */
    private final static TransactionStorage transactionStorage = new TransactionStorage();

    /**
     * Сервис уведомлений
     */
    private final static NotificationService notificationService = new NotificationService();

    /**
     * Сервис управления пользователями
     */
    private final static UserService userService = new UserService(userStorage);

    /**
     * Сервис управления бюджетом
     */
    private final static BudgetService budgetService = new BudgetService(notificationService);

    /**
     * Сервис управления финансовыми целями
     */
    private final static GoalService goalService = new GoalService(notificationService);

    /**
     * Сервис аналитики транзакций
     */
    private final static AnalyticsService analyticsService = new AnalyticsService(transactionStorage);

    /**
     * Сервис управления транзакциями
     */
    private final static TransactionService transactionService = new TransactionService(transactionStorage, budgetService, goalService);

    /**
     * Обработчик команд транзакций
     */
    private final static TransactionConsoleHandler transactionConsoleHandler = new TransactionConsoleHandler(
            transactionService,
            budgetService,
            goalService,
            analyticsService,
            notificationService
    );

    /**
     * Сервис администрирования
     */
    private final static AdminService adminService = new AdminService(userService, transactionService);

    /**
     * Обработчик команд пользователей
     */
    private final static UserConsoleHandler userConsoleHandler = new UserConsoleHandler(
            userService,
            transactionService,
            transactionConsoleHandler,
            adminService
    );

    /**
     * Возвращает хранилище пользователей.
     *
     * @return хранилище пользователей
     */
    public static UserStorage getUserStorage() {
        return userStorage;
    }

    /**
     * Возвращает хранилище транзакций.
     *
     * @return хранилище транзакций
     */
    public static TransactionStorage getTransactionStorage() {
        return transactionStorage;
    }

    /**
     * Возвращает сервис уведомлений.
     *
     * @return сервис уведомлений
     */
    public static NotificationService getNotificationService() {
        return notificationService;
    }

    /**
     * Возвращает сервис управления пользователями.
     *
     * @return сервис пользователей
     */
    public static UserService getUserService() {
        return userService;
    }

    /**
     * Возвращает сервис управления бюджетом.
     *
     * @return сервис бюджета
     */
    public static BudgetService getBudgetService() {
        return budgetService;
    }

    /**
     * Возвращает сервис управления целями.
     *
     * @return сервис целей
     */
    public static GoalService getGoalService() {
        return goalService;
    }

    /**
     * Возвращает сервис аналитики.
     *
     * @return сервис аналитики
     */
    public static AnalyticsService getAnalyticsService() {
        return analyticsService;
    }

    /**
     * Возвращает сервис управления транзакциями.
     *
     * @return сервис транзакций
     */
    public static TransactionService getTransactionService() {
        return transactionService;
    }

    /**
     * Возвращает обработчик команд для транзакций.
     *
     * @return обработчик транзакций
     */
    public static TransactionConsoleHandler getTransactionConsoleHandler() {
        return transactionConsoleHandler;
    }

    /**
     * Возвращает обработчик команд для пользователей.
     *
     * @return обработчик пользователей
     */
    public static UserConsoleHandler getUserConsoleHandler() {
        return userConsoleHandler;
    }

    /**
     * Возвращает сервис администрирования.
     *
     * @return сервис администрирования
     */
    public static AdminService getAdminService() {
        return adminService;
    }
}