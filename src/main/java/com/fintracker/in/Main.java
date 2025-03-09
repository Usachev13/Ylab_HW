package com.fintracker.in;

import com.fintracker.core.models.User;
import com.fintracker.core.models.UserRole;
import com.fintracker.core.services.*;
import com.fintracker.out.AnalyticsService;
import com.fintracker.out.NotificationService;
import com.fintracker.storage.TransactionStorage;
import com.fintracker.storage.UserStorage;

public class Main {
    public static void main(String[] args) {

        UserStorage userStorage = new UserStorage();
        TransactionStorage transactionStorage = new TransactionStorage();


        NotificationService notificationService = new NotificationService();
        UserService userService = new UserService(userStorage);
        BudgetService budgetService = new BudgetService(notificationService);
        GoalService goalService = new GoalService(notificationService);
        AnalyticsService analyticsService = new AnalyticsService(transactionStorage);
        TransactionService transactionService = new TransactionService(transactionStorage, budgetService, goalService);


        userService.register(new User("Admin", "admin@fintracker.com", "admin123", UserRole.ADMIN));


        UserConsoleHandler userConsoleHandler = new UserConsoleHandler(userService,transactionService);
        TransactionConsoleHandler transactionConsoleHandler = new TransactionConsoleHandler(transactionService, budgetService, goalService, analyticsService, notificationService);


        System.out.println("Добро пожаловать в финансовый трекер!");
        userConsoleHandler.start();
        transactionConsoleHandler.start();
    }
}