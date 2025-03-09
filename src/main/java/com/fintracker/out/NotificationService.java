package com.fintracker.out;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<String> notifications = new ArrayList<>();

    public void notifyBudgetExceeded(double budget, double expenses) {
        String message = "Внимание! Вы превысили месячный бюджет! Бюджет: " + budget + ", Расходы: " + expenses;
        notifications.add(message);
        System.out.println(message);
    }

    public void notifyGoalAchieved(double goal) {
        String message = "Поздравляем! Вы достигли своей финансовой цели: " + goal;
        notifications.add(message);
        System.out.println(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }
}

