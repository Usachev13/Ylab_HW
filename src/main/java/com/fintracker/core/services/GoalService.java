package com.fintracker.core.services;

import com.fintracker.out.NotificationService;

public class GoalService {
    private final NotificationService notificationService;

    public GoalService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private double targetAmount = 0;
    private double currentSavings = 0;

    public void setGoal(double amount) {
        this.targetAmount = amount;
        this.currentSavings = 0;
        System.out.println("Цель по накоплению установлена: " + amount);
    }

    public void addIncome(double income) {
        this.currentSavings += income;
        checkProgress();
    }

    private void checkProgress() {
        System.out.println("Текущий прогресс накоплений: " + currentSavings + " / " + targetAmount);
        if (currentSavings >= targetAmount) {
            notificationService.notifyGoalAchieved(targetAmount);
        }
    }

    public double getRemainingAmount() {
        return targetAmount - currentSavings;
    }
}
