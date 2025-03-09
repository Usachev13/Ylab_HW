import com.fintracker.core.models.User;
import com.fintracker.core.models.UserRole;
import com.fintracker.core.services.UserService;
import com.fintracker.storage.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserStorage userStorage;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerShouldAddUserWhenEmailNotExist() {
        User newUser = new User("Alex", "alex@email.com", "correctPassword",UserRole.USER);
        when(userStorage.getUserByEmail(newUser.getEmail())).thenReturn(null);
        User registeredUser = userService.register(newUser);
        assertThat(registeredUser).isNotNull();
        verify(userStorage).addUser(newUser);
    }

    @Test
    void registerShouldReturnNullWhenEmailAlreadyExist() {
        User existUser = new User("TestName", "test@email.com", "correctPassword",UserRole.USER);
        when(userStorage.getUserByEmail(existUser.getEmail())).thenReturn(existUser);
        User registeredUser = userService.register(existUser);
        assertThat(registeredUser).isNull();
        verify(userStorage, never()).addUser(any());
    }

    @Test
    void loginShouldSucceedWhenCredentialsAreCorrect() {
        User user = new User("TestName", "test@email.com", "correctPassword",UserRole.USER);
        when(userStorage.getUserByEmail(user.getEmail())).thenReturn(user);
        userService.login(user.getEmail(), user.getPassword());
    }

    @Test
    void loginShouldFailWhenPasswordIsIncorrect() {
        User user = new User("TestName", "test@email.com", "correctPassword", UserRole.USER);
        when(userStorage.getUserByEmail(user.getEmail())).thenReturn(user);
        userService.login(user.getEmail(), "WrongPassword");
    }
    @Test
    void loginShouldFailWhenUserNotFound(){
        String email = "unknown@email.com";
        when(userStorage.getUserByEmail(email)).thenReturn(null);

        userService.login(email,"somePassword");

    }
}