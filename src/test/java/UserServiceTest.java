import com.fintracker.core.service.UserService;
import com.fintracker.core.model.User;
import com.fintracker.core.model.UserRole;
import com.fintracker.storage.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserStorage userStorage;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userStorage);
    }

    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    void registerShouldCreateUserWhenNotExists() {
        User user = new User("John", "john@mail.com", "password123", UserRole.USER);

        when(userStorage.getUserByEmail(user.getEmail())).thenReturn(null);

        userService.register(user);

        verify(userStorage, times(1)).addUser(user);
    }

    @Test
    @DisplayName("Проверка, что пользователь не регистрируется, если уже существует")
    void registerShouldNotCreateUserWhenAlreadyExists() {
        User user = new User("John", "john@mail.com", "password123", UserRole.USER);

        when(userStorage.getUserByEmail(user.getEmail())).thenReturn(user);

        userService.register(user);

        verify(userStorage, never()).addUser(any());
    }

    @Test
    @DisplayName("Проверка успешного входа пользователя")
    void loginShouldSucceedWithCorrectCredentials() {
        User user = new User("John", "john@mail.com", "password123", UserRole.USER);

        when(userStorage.getUserByEmail(user.getEmail())).thenReturn(user);

        userService.login(user.getEmail(), user.getPassword());

        verify(userStorage, times(1)).getUserByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Должен обновить данные пользователя при редактировании")
    void editUserShouldUpdateUserDetails() {
        User existingUser = new User("John", "john@mail.com", "pass123", UserRole.USER);
        when(userStorage.getUserByEmail("john@mail.com")).thenReturn(existingUser);

        userService.editUser("john@mail.com", "Johnny", "johnny@mail.com", "newpass123");

        verify(userStorage).getUserByEmail("john@mail.com");
        verify(userStorage).updateUser(argThat(updatedUser ->
                updatedUser.getName().equals("Johnny") &&
                        updatedUser.getEmail().equals("johnny@mail.com") &&
                        updatedUser.getPassword().equals("newpass123")
        ));
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void deleteUserShouldRemoveUser() {
        User user = new User("John", "john@mail.com", "password123", UserRole.USER);

        when(userStorage.getUserByEmail(user.getEmail())).thenReturn(user);

        userService.deleteUser(user.getEmail());

        verify(userStorage, times(1)).removeUser(user.getEmail());
    }
}
