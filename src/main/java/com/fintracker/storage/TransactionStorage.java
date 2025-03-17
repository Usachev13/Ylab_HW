package com.fintracker.storage;

import com.fintracker.core.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс TransactionStorage обеспечивает хранение и управление транзакциями в памяти.
 * В качестве структуры хранения используется HashMap, ключом которой выступает уникальный ID транзакции.
 */
public class TransactionStorage {

    /** Хранилище транзакций, ключ – уникальный идентификатор транзакции. */
    private final HashMap<String, Transaction> transactions = new HashMap<>();

    /**
     * Добавляет новую транзакцию в хранилище.
     *
     * @param transaction объект транзакции для добавления
     */
    public void add(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    /**
     * Возвращает список всех сохраненных транзакций.
     *
     * @return список транзакций
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }

    /**
     * Возвращает транзакцию по её уникальному идентификатору.
     *
     * @param id уникальный идентификатор транзакции
     * @return найденная транзакция или null, если транзакция с таким ID не существует
     */
    public Transaction getById(String id) {
        return transactions.get(id);
    }

    /**
     * Обновляет существующую транзакцию по её идентификатору.
     *
     * @param id идентификатор транзакции для обновления
     * @param newTransaction новая версия транзакции, которая заменит старую
     */
    public void updateTransaction(String id, Transaction newTransaction) {
        if (transactions.containsKey(id)) {
            transactions.put(id, newTransaction);
        }
    }

    /**
     * Удаляет транзакцию из хранилища по её идентификатору.
     *
     * @param id идентификатор транзакции для удаления
     * @return true, если транзакция успешно удалена, иначе false
     */
    public boolean deleteTransaction(String id) {
        return transactions.remove(id) != null;
    }
}