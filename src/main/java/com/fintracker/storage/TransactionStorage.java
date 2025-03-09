package com.fintracker.storage;

import com.fintracker.core.models.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionStorage {
    HashMap<String, Transaction> transactions = new HashMap<>();

    public void add(Transaction transaction){transactions.put(transaction.getId(), transaction);
    }

    public List<Transaction> getAllTransactions(){
        return new ArrayList<>(transactions.values());
    }
    public Transaction getById(String id){
        return transactions.get(id);
    }
    public void updateTransaction(String id, Transaction newTransaction){
        if (transactions.containsKey(id)){
            transactions.put(id,newTransaction);
        }

    }

    public boolean deleteTransaction(String id){
        return transactions.remove(id) != null;
    }

}
