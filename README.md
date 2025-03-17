# Финансовый Трекер (Personal Finance Tracker)

## Описание проекта
"Финансовый Трекер" — это консольное приложение для управления личными финансами.  
Приложение позволяет пользователям:
- **Регистрироваться и входить в систему**
- **Добавлять, редактировать и удалять транзакции**
- **Отслеживать баланс, доходы и расходы**
- **Устанавливать цели накоплений и контролировать их выполнение**
- **Задавать бюджет и получать уведомления о превышении лимита**
- **Формировать отчёты по финансовому состоянию**
- **Администраторы могут управлять пользователями и их транзакциями**

## Используемые технологии
- **Java 17+** (чистая Java без Spring)
- **JUnit 5, Mockito, AssertJ** (для тестирования)
- **Gradle** (система сборки)
- **Коллекции Java** (`HashMap`, `ArrayList`) — для хранения данных в памяти

---

## Установка и запуск

### ** 1. Клонирование репозитория**
```sh
git clone https://github.com/Usachev13/Ylab_HW.git
```

---

## 📝 Как использовать?

После запуска приложения вводите команды в консоли.

### **📌 Основные команды**
| Команда          | Описание                                      |
|-----------------|----------------------------------------------|
| `registration`  | Регистрация нового пользователя             |
| `login`        | Вход в систему                              |
| `edit`         | Изменение данных пользователя              |
| `delete`       | Удаление аккаунта                          |
| `set_budget`   | Установка месячного бюджета                |
| `set_goal`     | Установка цели накопления                  |
| `check_goal`   | Проверка прогресса накоплений              |
| `add`          | Добавление новой транзакции                |
| `edit`         | Редактирование транзакции                  |
| `delete`       | Удаление транзакции                        |
| `list`         | Просмотр всех транзакций                   |
| `generate_report` | Формирование финансового отчёта       |
| `admin_panel`  | Доступ администратора к управлению        |
| `exit`         | Выход из программы                        |

---

## Админ-панель

Администраторы могут управлять пользователями и транзакциями.  
При входе в `admin_panel` введите email администратора.

### **Команды администратора**
| Команда            | Описание                          |
|-------------------|----------------------------------|
| `view_users`     | Просмотр всех пользователей      |
| `delete_user`    | Удаление пользователя           |
| `view_transactions` | Просмотр всех транзакций  |
| `exit`          | Выход из админ-панели |

---

## Тестирование

Для запуска тестов используйте команды:

```

**Ожидаемый результат**: Все тесты `UserServiceTest` и `TransactionServiceTest` должны проходить успешно.  
