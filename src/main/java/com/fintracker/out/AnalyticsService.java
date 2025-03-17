package com.fintracker.out;

import com.fintracker.core.model.Transaction;
import com.fintracker.core.model.TransactionType;
import com.fintracker.storage.TransactionStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для анализа финансовых транзакций и генерации отчетов.
 * <p>
 * Предоставляет методы расчета текущего баланса, группировки транзакций по категориям
 * и генерации детализированного финансового отчета.
 */
public class AnalyticsService {

    /**
     * Хранилище транзакций.
     */
    private final TransactionStorage storage;

    /**
     * Создает объект AnalyticsService с указанным хранилищем транзакций.
     *
     * @param storage объект {@link TransactionStorage} для получения транзакций
     */
    public AnalyticsService(TransactionStorage storage) {
        this.storage = storage;
    }

    /**
     * Рассчитывает текущий баланс на основе всех доступных транзакций.
     *
     * @return текущий баланс (доходы минус расходы)
     */
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

    /**
     * Группирует транзакции по категориям и рассчитывает сумму по каждой категории.
     *
     * @return карта, где ключ — категория транзакции, значение — общая сумма по этой категории
     */
    public Map<String, Double> getTransactionsByCategory() {
        return storage.getAllTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));
    }

    /**
     * Генерирует и выводит в консоль финансовый отчет.
     * <p>
     * Отчет содержит текущий баланс и суммы транзакций по категориям.
     */
    public void generateReport() {
        System.out.println("Финансовый отчет:");
        System.out.println("Текущий баланс: " + getBalance());
        System.out.println("Доходы и расходы по категориям:");
        getTransactionsByCategory().forEach((category, amount) ->
                System.out.println(category + ": " + amount));
    }
}