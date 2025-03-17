package com.fintracker.out;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления уведомлениями пользователя о состоянии бюджета и финансовых целей.
 * <p>
 * Отвечает за создание, хранение и выдачу уведомлений о превышении бюджета и достижении финансовых целей.
 */
public class NotificationService {

    /**
     * Список уведомлений для пользователя.
     */
    private final List<String> notifications = new ArrayList<>();

    /**
     * Создает уведомление о превышении установленного бюджета и добавляет его в список уведомлений.
     *
     * @param budget   установленный бюджет
     * @param expenses текущие расходы, превысившие бюджет
     */
    public void notifyBudgetExceeded(double budget, double expenses) {
        String message = "Внимание! Вы превысили месячный бюджет! Бюджет: " + budget + ", Расходы: " + expenses;
        notifications.add(message);
        System.out.println(message);
    }

    /**
     * Создает уведомление о достижении пользователем установленной финансовой цели и добавляет его в список уведомлений.
     *
     * @param goal сумма финансовой цели, которую достиг пользователь
     */
    public void notifyGoalAchieved(double goal) {
        String message = "Поздравляем! Вы достигли своей финансовой цели: " + goal;
        notifications.add(message);
        System.out.println(message);
    }

    /**
     * Возвращает список всех сохраненных уведомлений.
     *
     * @return список уведомлений
     */
    public List<String> getNotifications() {
        return notifications;
    }
}