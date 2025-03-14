package com.fintracker.core.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс, описывающий финансовую транзакцию пользователя.
 */
public class Transaction {

    /** Уникальный идентификатор транзакции. */
    private String id;

    /** Сумма транзакции. */
    private double amount;

    /** Категория транзакции. */
    private String category;

    /** Дата транзакции. */
    private LocalDate date;

    /** Описание транзакции. */
    private String description;

    /** Тип транзакции (доход или расход). */
    private TransactionType type;

    /**
     * Создает объект транзакции с указанными параметрами.
     *
     * @param amount сумма транзакции
     * @param category категория транзакции
     * @param date дата транзакции
     * @param description описание транзакции
     * @param type тип транзакции
     */
    public Transaction(double amount, String category, LocalDate date, String description, TransactionType type) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    /**
     * Возвращает идентификатор транзакции.
     *
     * @return идентификатор транзакции
     */
    public String getId() {
        return id;
    }

    /**
     * Возвращает сумму транзакции.
     *
     * @return сумма транзакции
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Возвращает категорию транзакции.
     *
     * @return категория транзакции
     */
    public String getCategory() {
        return category;
    }

    /**
     * Возвращает дату транзакции.
     *
     * @return дата транзакции
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Возвращает описание транзакции.
     *
     * @return описание транзакции
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает тип транзакции.
     *
     * @return тип транзакции
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Возвращает строковое представление транзакции.
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}