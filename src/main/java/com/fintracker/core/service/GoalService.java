package com.fintracker.core.service;

import com.fintracker.core.model.User;
import com.fintracker.out.NotificationService;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис управления финансовыми целями пользователя.
 * Отвечает за установку целей накопления, отслеживание прогресса и отправку уведомлений при достижении цели.
 */
public class GoalService {

    /**
     * Сервис уведомлений для информирования пользователя о достижении цели.
     */
    private final NotificationService notificationService;

    /**
     * Карта для хранения финансовых целей пользователей (ключ — email пользователя, значение — сумма цели).
     */
    private final Map<String, Double> userGoals = new HashMap<>();

    /**
     * Карта для хранения текущих накоплений пользователей (ключ — email пользователя, значение — сумма накоплений).
     */
    private final Map<String, Double> userSavings = new HashMap<>();

    /**
     * Конструктор сервиса целей накопления.
     *
     * @param notificationService сервис уведомлений
     */
    public GoalService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Устанавливает финансовую цель для пользователя.
     *
     * @param user   пользователь, которому устанавливается цель
     * @param amount сумма, которую пользователь планирует накопить
     */
    public void setGoal(User user, double amount) {
        userGoals.put(user.getEmail(), amount);
        System.out.println("Цель по накоплению установлена: " + amount);
    }

    /**
     * Добавляет доход к текущим накоплениям пользователя.
     * После добавления проверяется, достигнута ли цель накопления.
     *
     * @param user   пользователь, чьи накопления обновляются
     * @param income сумма дохода, добавляемого к накоплениям
     */
    public void addIncome(User user, double income) {
        userSavings.put(user.getEmail(), userSavings.getOrDefault(user.getEmail(), 0.0) + income);
        checkProgress(user);
    }

    /**
     * Проверяет текущий прогресс накоплений пользователя относительно установленной цели.
     * Если цель достигнута или превышена, отправляет уведомление через сервис уведомлений.
     *
     * @param user пользователь, чей прогресс проверяется
     */
    private void checkProgress(User user) {
        double savings = userSavings.getOrDefault(user.getEmail(), 0.0);
        double goal = userGoals.getOrDefault(user.getEmail(), 0.0);
        System.out.println("Текущий прогресс накоплений: " + savings + "/" + goal);
        if (savings >= goal) {
            notificationService.notifyGoalAchieved(goal);
        }
    }

    /**
     * Возвращает сумму, которую пользователю осталось накопить для достижения цели.
     *
     * @param user пользователь, чьи накопления проверяются
     * @return оставшаяся сумма до достижения цели; отрицательное значение означает, что цель уже достигнута
     */
    public double getRemainingAmount(User user) {
        double goal = userGoals.getOrDefault(user.getEmail(), 0.0);
        double savings = userSavings.getOrDefault(user.getEmail(), 0.0);
        return goal - savings;
    }
}