package com.fintracker.in;

import com.fintracker.core.exception.ExitFromFinanceModuleException;
import com.fintracker.core.model.Transaction;
import com.fintracker.core.model.TransactionType;
import com.fintracker.core.model.User;
import com.fintracker.out.AnalyticsService;
import com.fintracker.core.service.BudgetService;
import com.fintracker.core.service.GoalService;
import com.fintracker.core.service.TransactionService;
import com.fintracker.out.NotificationService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Консольный обработчик финансовых операций пользователя.
 * Предоставляет интерфейс для управления транзакциями, бюджетом, финансовыми целями и отчетами.
 */
public class TransactionConsoleHandler {
    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final GoalService goalService;
    private final AnalyticsService analyticsService;
    private final NotificationService notificationService;
    Scanner scanner = new Scanner(System.in);
    private User currentUser;

    /**
     * Конструктор класса TransactionConsoleHandler.
     *
     * @param transactionService  сервис управления транзакциями
     * @param budgetService       сервис управления бюджетом
     * @param goalService         сервис управления финансовыми целями
     * @param analyticsService    сервис для генерации отчетов и анализа
     * @param notificationService сервис для работы с уведомлениями
     */
    public TransactionConsoleHandler(TransactionService transactionService, BudgetService budgetService, GoalService goalService, AnalyticsService analyticsService, NotificationService notificationService) {
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.goalService = goalService;
        this.analyticsService = analyticsService;
        this.notificationService = notificationService;
    }

    /**
     * Устанавливает месячный бюджет для текущего пользователя.
     */

    private void handleSetBudget() {
        System.out.println("Введите сумму месячного бюджета: ");
        double amount = Double.parseDouble(scanner.nextLine());
        budgetService.setBudget(currentUser, amount);
    }

    /**
     * Устанавливает финансовую цель для текущего пользователя.
     */

    private void handleSetGoal() {
        System.out.println("Введите сумму, которую хотите накопить: ");
        double amount = Double.parseDouble(scanner.nextLine());
        goalService.setGoal(currentUser, amount);
    }

    /**
     * Проверяет текущий прогресс по финансовой цели.
     */

    private void handleCheckGoal() {
        double remaining = goalService.getRemainingAmount(currentUser);
        if (remaining <= 0) {
            System.out.println("Вы уже достигли своей финансовой цели! 🎉");
        } else {
            System.out.println("Осталось накопить: " + remaining);
        }
    }

    /**
     * Добавляет новую транзакцию для текущего пользователя.
     */

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
        transactionService.createTransaction(currentUser, transaction);
        System.out.println("Транзакция успешно добавлена!");
    }

    /**
     * Редактирует существующую транзакцию.
     */

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

    /**
     * Удаляет транзакцию по идентификатору.
     */

    private void handleDeleteTransaction() {
        System.out.println("Введите ID транзакции для удаления: ");
        String id = scanner.nextLine();
        if (transactionService.deleteTransaction(id)) {
            System.out.println("Транзакция удалена!");
        } else {
            System.out.println("Транзакция не найдена!");
        }
    }

    /**
     * Выводит список всех транзакций текущего пользователя.
     */

    private void handleListTransaction() {
        System.out.println("Список всех транзакций: ");
        for (Transaction transaction : transactionService.listTransactions()) {
            System.out.println(transaction);
        }
    }

    /**
     * Создает аналитический отчет.
     */

    private void handleGenerateReport() {
        analyticsService.generateReport();
    }

    /**
     * Проверяет наличие уведомлений и выводит их пользователю.
     */

    private void handleNotifications() {
        System.out.println("Уведомления:");
        List<String> notifications = notificationService.getNotifications();
        if (notifications.isEmpty()) {
            System.out.println("Нет новых уведомлений.");
        } else {
            notifications.forEach(System.out::println);
        }
    }

    /**
     * Запускает консольный интерфейс финансового управления для текущего пользователя.
     *
     * @param currentUser текущий авторизованный пользователь
     */
    public void start(User currentUser) {
        this.currentUser = currentUser;
        while (true) {
            System.out.println("\nВведите команду(" +
                    "1 - установить бюджет," +
                    "2 - установить цель," +
                    "3 - проверить накопления," +
                    "\n4 - добавить тразакцию," +
                    "5 - изменить транзакцию," +
                    "6 - удалить тразакцию," +
                    "\n7 - список тразакций," +
                    "8 - создать отчет," +
                    "9 - проверить уведомления," +
                    "10 - выйти): ");
            processCommands(transactionCommands());
        }
    }

    /**
     * Обрабатывает введенные команды и вызывает соответствующие методы.
     *
     * @param commands словарь доступных команд и действий
     */

    private void processCommands(Map<String, Runnable> commands) {
        String command = scanner.nextLine();
        commands.getOrDefault(command, () -> System.out.println("Неверная команда, попробуйте еще раз!")).run();
    }

    /**
     * Возвращает карту команд и их обработчиков для управления финансами.
     *
     * @return карта команд
     */

    private Map<String, Runnable> transactionCommands() {
        Map<String, Runnable> command = new HashMap<>();
        command.put("1", this::handleSetBudget);
        command.put("2", this::handleSetGoal);
        command.put("3", this::handleCheckGoal);
        command.put("4", this::handleAddTransaction);
        command.put("5", this::handleEditTransaction);
        command.put("6", this::handleDeleteTransaction);
        command.put("7", this::handleListTransaction);
        command.put("8", this::handleGenerateReport);
        command.put("9", this::handleNotifications);
        command.put("10", () -> {
            System.out.println("Выход из модуля с финансами...");
            throw new ExitFromFinanceModuleException();
        });
        return command;

    }
}

