package com.fintracker.core.service;

import com.fintracker.core.model.Transaction;
import com.fintracker.core.model.User;

import java.util.List;

/**
 * Сервис для выполнения административных действий.
 * Предоставляет возможность просмотра всех пользователей и транзакций,
 * а также удаления пользователя по email.
 */
public class AdminService {

    /**
     * Сервис для работы с пользователями.
     */
    private final UserService userService;

    /**
     * Сервис для работы с транзакциями пользователей.
     */
    private final TransactionService transactionService;

    /**
     * Конструктор AdminService.
     *
     * @param userService        сервис управления пользователями
     * @param transactionService сервис управления транзакциями
     */
    public AdminService(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    /**
     * Возвращает список всех зарегистрированных пользователей.
     *
     * @return список всех пользователей
     */
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    /**
     * Удаляет пользователя по указанному email.
     *
     * @param email email пользователя, которого требуется удалить
     * @return true, если пользователь был успешно удалён, false - если пользователя с таким email нет
     */
    public boolean deleteUserByEmail(String email) {
        return userService.deleteUserByEmail(email);
    }

    /**
     * Возвращает список всех транзакций пользователей.
     *
     * @return список всех транзакций
     */
    public List<Transaction> getAllTransaction() {
        return transactionService.listTransactions();
    }
}