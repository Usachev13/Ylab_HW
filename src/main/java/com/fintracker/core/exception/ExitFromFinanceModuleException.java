package com.fintracker.core.exception;

/**
 * Исключение, сигнализирующее о необходимости выхода из модуля управления финансами.
 * <p>
 * Используется для прерывания текущего цикла выполнения и возвращения в предыдущее меню.
 */
public class ExitFromFinanceModuleException extends RuntimeException {
    /**
     * Конструктор исключения без сообщения.
     */
    public ExitFromFinanceModuleException() {
        super();
    }

    /**
     * Конструктор исключения с заданным сообщением.
     *
     * @param message сообщение с описанием причины исключения
     */
    public ExitFromFinanceModuleException(String message) {
        super(message);
    }
}