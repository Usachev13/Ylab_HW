package com.fintracker.core.service;

import com.fintracker.core.model.User;
import com.fintracker.out.NotificationService;

/**
 * Сервис управления бюджетом пользователя.
 * Предоставляет функционал для установки ежемесячного бюджета,
 * отслеживания расходов и отправки уведомлений при превышении бюджета.
 */
public class BudgetService {

    /**
     * Сервис уведомлений для информирования пользователя о превышении бюджета.
     */
    private final NotificationService notificationService;

    /**
     * Ежемесячный бюджет пользователя.
     */
    private double monthlyBudget = 0;

    /**
     * Сумма всех расходов пользователя за текущий месяц.
     */
    private double totalExpenses = 0;

    /**
     * Конструктор сервиса бюджета.
     *
     * @param notificationService сервис уведомлений
     */
    public BudgetService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Устанавливает ежемесячный бюджет пользователя.
     *
     * @param user   пользователь, для которого устанавливается бюджет
     * @param amount сумма ежемесячного бюджета
     */
    public void setBudget(User user, double amount) {
        this.monthlyBudget = amount;
        System.out.println("Бюджет установлен: " + amount);
    }

    /**
     * Добавляет сумму расходов в текущий бюджет и проверяет, не превышен ли установленный бюджет.
     * В случае превышения бюджета отправляется уведомление пользователю.
     *
     * @param expense сумма расхода, добавляемого к текущим расходам
     */
    public void addExpense(double expense) {
        this.totalExpenses += expense;
        checkBudget();
    }

    /**
     * Проверяет, не превышен ли текущий бюджет суммой расходов.
     * Если расходы превышают установленный бюджет, отправляет уведомление пользователю.
     */
    private void checkBudget() {
        if (totalExpenses > monthlyBudget) {
            notificationService.notifyBudgetExceeded(monthlyBudget, totalExpenses);
        }
    }

    /**
     * Возвращает оставшуюся сумму бюджета на текущий месяц.
     *
     * @return оставшаяся сумма бюджета (если отрицательная, бюджет превышен)
     */
    public double getRemainingBudget() {
        return monthlyBudget - totalExpenses;
    }
}