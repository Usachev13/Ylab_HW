import com.fintracker.core.models.Transaction;
import com.fintracker.core.models.TransactionType;
import com.fintracker.core.services.TransactionService;
import com.fintracker.storage.TransactionStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    @Mock
    TransactionStorage storage;
    @InjectMocks
    TransactionService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransactionShouldAddTransaction() {
        Transaction transaction = new Transaction(100, "testCategory", LocalDate.now(), "TestDescription", TransactionType.EXPENSE);
        service.createTransaction(transaction);
        verify(storage, times(1)).add(transaction);
    }

    @Test
    void editTransactionShouldExistingTransaction() {
        String id = "12345";
        Transaction transaction = new Transaction(100, "testCategory", LocalDate.now(), "TestDescription", TransactionType.EXPENSE);
        Transaction updateTransaction = new Transaction(150, "anotherCategory", LocalDate.now(), "anotherDescription", TransactionType.INCOME);
        when(storage.getById(id)).thenReturn(transaction);
        Transaction result = service.editTransaction(id, updateTransaction);

        assertThat(result).isNotNull();
        verify(storage).updateTransaction(id, updateTransaction);
    }

    @Test
    void editTransaction_ShouldReturnNull_WhenTransactionNotFound() {
        String transactionId = "not_exist";
        Transaction updatedTransaction = new Transaction(150.0, "Grocery", LocalDate.now(), "Supermarket", TransactionType.EXPENSE);

        when(storage.getById(transactionId)).thenReturn(null);

        Transaction result = service.editTransaction(transactionId, updatedTransaction);

        assertThat(result).isNull();
        verify(storage, never()).updateTransaction(any(), any());
    }

    @Test
    void deleteTransaction_ShouldReturnTrue_WhenTransactionExists() {

        String transactionId = "12345";
        when(storage.deleteTransaction(transactionId)).thenReturn(true);
        boolean result = service.deleteTransaction(transactionId);
        assertThat(result).isTrue();
        verify(storage).deleteTransaction(transactionId);
    }

    @Test
    void deleteTransaction_ShouldReturnFalse_WhenTransactionNotFound() {

        String transactionId = "not_exist";
        when(storage.deleteTransaction(transactionId)).thenReturn(false);

        boolean result = service.deleteTransaction(transactionId);

        assertThat(result).isFalse();
        verify(storage).deleteTransaction(transactionId);
    }

    @Test
    void listTransactions_ShouldReturnAllTransactions() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "Food", LocalDate.now(), "Lunch", TransactionType.EXPENSE),
                new Transaction(200.0, "Salary", LocalDate.now(), "Monthly Salary", TransactionType.INCOME)
        );
        when(storage.getAllTransactions()).thenReturn(transactions);

        List<Transaction> result = service.listTransactions();

        assertThat(result).hasSize(2);
        verify(storage).getAllTransactions();
    }
}
