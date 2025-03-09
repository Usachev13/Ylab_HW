package com.fintracker.core.services;

import com.fintracker.out.NotificationService;

public class BudgetService {

    private final NotificationService notificationService;

    public BudgetService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private double monthlyBudget = 0;
    private double totalExpenses = 0;

    public void setBudget(double amount) {
        this.monthlyBudget = amount;
        System.out.println("Бюджет установлен: " + amount);
    }

    public void addExpense(double expense) {
        this.totalExpenses += expense;
        checkBudget();
    }

    private void checkBudget() {
        if (totalExpenses > monthlyBudget) {
            notificationService.notifyBudgetExceeded(monthlyBudget, totalExpenses);
        }
    }

    public double getRemainingBudget() {
        return monthlyBudget - totalExpenses;
    }
}
