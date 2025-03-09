package com.fintracker.core.services;


import com.fintracker.core.models.Transaction;
import com.fintracker.core.models.TransactionType;
import com.fintracker.storage.TransactionStorage;

import java.util.List;

public class TransactionService {
    private final TransactionStorage storage;
    private final BudgetService budgetService;
    private final GoalService goalService;

    public TransactionService(TransactionStorage storage, BudgetService budgetService, GoalService goalService) {
        this.storage = storage;
        this.budgetService = budgetService;
        this.goalService = goalService;
    }

    public void createTransaction(Transaction transaction) {
        storage.add(transaction);
        if (transaction.getType() == TransactionType.EXPENSE) {
            budgetService.addExpense(transaction.getAmount());
        } else if (transaction.getType() == TransactionType.INCOME) {
            goalService.addIncome(transaction.getAmount());
        }
    }
    public Transaction editTransaction(String id, Transaction transaction){
        if (storage.getById(id) != null){
          storage.updateTransaction(id,transaction);
          return transaction;
        }
        System.out.println("Такой транзакции нет!");
        return null;
    }
    public boolean deleteTransaction(String id){
        return storage.deleteTransaction(id);
    }
    public List<Transaction>listTransactions(){
        return storage.getAllTransactions();
    }

}
