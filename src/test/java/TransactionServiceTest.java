import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import com.fintracker.core.model.Transaction;
import com.fintracker.core.model.TransactionType;
import com.fintracker.core.model.User;
import com.fintracker.core.service.TransactionService;
import com.fintracker.core.service.GoalService;
import com.fintracker.core.service.BudgetService;
import com.fintracker.storage.TransactionStorage;

@DisplayName("Тесты TransactionService")
class TransactionServiceTest {

    private final TransactionStorage storage = mock(TransactionStorage.class);
    private final BudgetService budgetService = mock(BudgetService.class);
    private final GoalService goalService = mock(GoalService.class);
    private final TransactionService transactionService = new TransactionService(storage, budgetService, goalService);

    @Test
    @DisplayName("Создание транзакции типа EXPENSE должно сохранять ее в хранилище и обновлять бюджет")
    void createExpenseTransactionShouldAddToStorageAndBudget() {
        Transaction transaction = new Transaction(100, "Еда", LocalDate.now(), "Обед", TransactionType.EXPENSE);
        User user = new User("Иван", "ivan@test.com", "pass123", null);

        transactionService.createTransaction(user, transaction);

        verify(storage).add(transaction);
        verify(budgetService).addExpense(100);
        verify(goalService, never()).addIncome(any(), anyDouble());
    }

    @Test
    @DisplayName("Создание транзакции типа INCOME должно сохранять ее в хранилище и обновлять накопления, если цель не достигнута")
    void createIncomeTransactionShouldAddToStorageAndGoalIfGoalNotReached() {
        Transaction transaction = new Transaction(200, "Зарплата", LocalDate.now(), "Зарплата за январь", TransactionType.INCOME);
        User user = new User("Анна", "anna@test.com", "pass456", null);

        when(goalService.getRemainingAmount(user)).thenReturn(500.0);

        transactionService.createTransaction(user, transaction);

        verify(storage).add(transaction);
        verify(goalService).addIncome(user, 200);
        verify(budgetService, never()).addExpense(anyDouble());
    }

    @Test
    @DisplayName("Создание транзакции типа INCOME не должно обновлять накопления, если цель уже достигнута")
    void createIncomeTransactionShouldNotAddToGoalIfGoalReached() {
        Transaction transaction = new Transaction(200, "Фриланс", LocalDate.now(), "Проект", TransactionType.INCOME);
        User user = new User("Олег", "oleg@test.com", "pass789", null);

        when(goalService.getRemainingAmount(user)).thenReturn(0.0);

        transactionService.createTransaction(user, transaction);

        verify(storage).add(transaction);
        verify(goalService, never()).addIncome(any(), anyDouble());
    }

    @Test
    @DisplayName("Редактирование существующей транзакции должно обновить данные в хранилище")
    void editExistingTransactionShouldUpdateStorage() {
        String transactionId = "12345";
        Transaction originalTransaction = new Transaction(100, "Покупки", LocalDate.now(), "Старое описание", TransactionType.EXPENSE);
        Transaction updatedTransaction = new Transaction(150, "Покупки", LocalDate.now(), "Новое описание", TransactionType.EXPENSE);

        when(storage.getById(transactionId)).thenReturn(originalTransaction);

        transactionService.editTransaction(transactionId, updatedTransaction);

        verify(storage).updateTransaction(transactionId, updatedTransaction);
    }

    @Test
    @DisplayName("Удаление транзакции должно удалять ее из хранилища")
    void deleteTransactionShouldRemoveFromStorage() {
        String transactionId = "12345";

        when(storage.deleteTransaction(transactionId)).thenReturn(true);

        boolean result = transactionService.deleteTransaction(transactionId);

        assertTrue(result, "Удаление транзакции должно вернуть true");
        verify(storage).deleteTransaction(transactionId);
    }
}