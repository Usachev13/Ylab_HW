package com.fintracker.out;

import com.fintracker.core.models.Transaction;
import com.fintracker.core.models.TransactionType;
import com.fintracker.storage.TransactionStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsService {
    private final TransactionStorage storage;

    public AnalyticsService(TransactionStorage storage) {
        this.storage = storage;
    }

    public double getBalance() {
        List<Transaction> transactions = storage.getAllTransactions();
        double income = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
        double expenses = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
        return income - expenses;
    }

    public Map<String, Double> getTransactionsByCategory() {
        return storage.getAllTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));
    }

    public void generateReport() {
        System.out.println("Финансовый отчет:");
        System.out.println("Текущий баланс: " + getBalance());
        System.out.println("Доходы и расходы по категориям:");
        getTransactionsByCategory().forEach((category, amount) ->
                System.out.println(category + ": " + amount));
    }
}
