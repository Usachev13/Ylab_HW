package com.fintracker.core.models;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private String id;
    private double amount;
    private String category;

    private LocalDate date;
    private String description;
    private TransactionType type;

    public Transaction(double amount, String category, LocalDate date, String description, TransactionType type) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }

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


