package com.fintracker.core.service;

import com.fintracker.core.model.Transaction;
import com.fintracker.core.model.TransactionType;
import com.fintracker.core.model.User;
import com.fintracker.storage.TransactionStorage;

import java.util.List;

/**
 * Сервис для управления транзакциями.
 * Отвечает за создание, редактирование, удаление и получение списка транзакций.
 */
public class TransactionService {

    /**
     * Хранилище для транзакций.
     */
    private final TransactionStorage storage;

    /**
     * Сервис управления бюджетом пользователя.
     */
    private final BudgetService budgetService;

    /**
     * Сервис управления целями по накоплениям пользователя.
     */
    private final GoalService goalService;

    /**
     * Конструктор сервиса транзакций.
     *
     * @param storage       хранилище транзакций
     * @param budgetService сервис для работы с бюджетом
     * @param goalService   сервис для работы с финансовыми целями
     */
    public TransactionService(TransactionStorage storage, BudgetService budgetService, GoalService goalService) {
        this.storage = storage;
        this.budgetService = budgetService;
        this.goalService = goalService;
    }

    /**
     * Создает и сохраняет новую транзакцию пользователя.
     * В зависимости от типа транзакции (расход или доход) обновляет бюджет или накопления.
     *
     * @param user        пользователь, совершающий транзакцию
     * @param transaction транзакция для добавления
     */
    public void createTransaction(User user, Transaction transaction) {
        storage.add(transaction);
        if (transaction.getType() == TransactionType.EXPENSE) {
            budgetService.addExpense(transaction.getAmount());
        } else if (transaction.getType() == TransactionType.INCOME) {
            if (goalService.getRemainingAmount(user) > 0) {
                goalService.addIncome(user, transaction.getAmount());
            }
        }
    }

    /**
     * Редактирует существующую транзакцию.
     *
     * @param id          уникальный идентификатор транзакции
     * @param transaction объект транзакции с новыми данными
     * @return обновлённая транзакция или null, если транзакция не найдена
     */
    public Transaction editTransaction(String id, Transaction transaction) {
        if (storage.getById(id) != null) {
            storage.updateTransaction(id, transaction);
            return transaction;
        }
        System.out.println("Такой транзакции нет!");
        return null;
    }

    /**
     * Удаляет транзакцию по её уникальному идентификатору.
     *
     * @param id уникальный идентификатор транзакции
     * @return true, если транзакция успешно удалена, иначе false
     */
    public boolean deleteTransaction(String id) {
        return storage.deleteTransaction(id);
    }

    /**
     * Получает список всех транзакций.
     *
     * @return список всех сохраненных транзакций
     */
    public List<Transaction> listTransactions() {
        return storage.getAllTransactions();
    }
}