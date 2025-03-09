package com.fintracker.in;

import com.fintracker.core.models.Transaction;
import com.fintracker.core.models.TransactionType;
import com.fintracker.out.AnalyticsService;
import com.fintracker.core.services.BudgetService;
import com.fintracker.core.services.GoalService;
import com.fintracker.core.services.TransactionService;
import com.fintracker.out.NotificationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransactionConsoleHandler {
    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final GoalService goalService;
    private final AnalyticsService analyticsService;
    private final NotificationService notificationService;

    public TransactionConsoleHandler(TransactionService transactionService, BudgetService budgetService, GoalService goalService, AnalyticsService analyticsService, NotificationService notificationService) {
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.goalService = goalService;
        this.analyticsService = analyticsService;
        this.notificationService = notificationService;
    }

    Scanner scanner = new Scanner(System.in);

    private void handleSetBudget() {
        System.out.println("Введите сумму месячного бюджета: ");
        double amount = Double.parseDouble(scanner.nextLine());
        budgetService.setBudget(amount);
    }

    private void handleSetGoal() {
        System.out.println("Введите сумму, которую хотите накопить: ");
        double amount = Double.parseDouble(scanner.nextLine());
        goalService.setGoal(amount);
    }

    private void handleCheckGoal() {
        double remaining = goalService.getRemainingAmount();
        if (remaining <= 0) {
            System.out.println("Вы уже достигли своей финансовой цели! 🎉");
        } else {
            System.out.println("Осталось накопить: " + remaining);
        }
    }

    private void handleAddTransaction() {
        System.out.println("Введите сумму: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите категорию: ");
        String category = scanner.nextLine();
        System.out.println("Введите дату ГГГГ-ММ-ДД: ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите описание: ");
        String description = scanner.nextLine();
        System.out.println("Выберите тип INCOME/EXPENSE: ");
        TransactionType type = TransactionType.valueOf(scanner.nextLine().toUpperCase());
        Transaction transaction = new Transaction(amount, category, date, description, type);
        transactionService.createTransaction(transaction);
        System.out.println("Транзакция успешно добавлена!");
    }

    private void handleEditTransaction() {
        System.out.println("Введите ID транзакции, которую хотите изменить: ");
        String id = scanner.nextLine();
        System.out.println("Введите новую сумму: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите новую категорию: ");
        String category = scanner.nextLine();
        System.out.println("Введите новую дату ГГГГ-ММ-ДД: ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите новое описание: ");
        String description = scanner.nextLine();
        System.out.println("Выберите новый тип INCOME/EXPENSE: ");
        TransactionType type = TransactionType.valueOf(scanner.nextLine().toUpperCase());
        Transaction transaction = new Transaction(amount, category, date, description, type);
        transactionService.editTransaction(id, transaction);
        System.out.println("Транзакция успешно изменена!");
    }

    private void handleDeleteTransaction() {
        System.out.println("Введите ID транзакции для удаления: ");
        String id = scanner.nextLine();
        if (transactionService.deleteTransaction(id)) {
            System.out.println("Транзакция удалена!");
        } else {
            System.out.println("Транзакция не найдена!");
        }
    }

    private void handleListTransaction() {
        System.out.println("Список всех транзакций: ");
        for (Transaction transaction : transactionService.listTransactions()) {
            System.out.println(transaction);
        }
    }

    private void handleGenerateReport() {
        analyticsService.generateReport();
    }

    private void handleNotifications() {
        System.out.println("Уведомления:");
        List<String> notifications = notificationService.getNotifications();
        if (notifications.isEmpty()) {
            System.out.println("Нет новых уведомлений.");
        } else {
            notifications.forEach(System.out::println);
        }
    }


    public void start() {
        while (true) {
            System.out.println("Введите команду(setBudget,set_goal,check_goal, add, edit, delete, list,generate_report,notification, exit): ");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "set_budget":
                    handleSetBudget();
                    break;
                case "set_goal":
                    handleSetGoal();
                    break;
                case "check_goal":
                    handleCheckGoal();
                    break;
                case "add":
                    handleAddTransaction();
                    break;
                case "edit":
                    handleEditTransaction();
                    break;
                case "delete":
                    handleDeleteTransaction();
                    break;
                case "list":
                    handleListTransaction();
                    break;
                case "generate_report":
                    handleGenerateReport();
                    break;
                case "notifications":
                    handleNotifications();
                    break;
                case "exit":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Данная команда не обнаружена, попробуйте еще раз");
            }
        }
    }

}
