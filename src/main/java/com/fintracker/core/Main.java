package com.fintracker.core;
import com.fintracker.core.factory.AppFactory;
import com.fintracker.core.model.User;
import com.fintracker.core.model.UserRole;
import com.fintracker.in.TransactionConsoleHandler;
import com.fintracker.in.UserConsoleHandler;

import java.sql.*;


public class Main {

    public static void main(String[] args) {
         var userService = AppFactory.getUserService();


     userService.register(new User("Admin", "admin@fintracker.com", "admin123", UserRole.ADMIN));


        System.out.println("Добро пожаловать в финансовый трекер!");
        UserConsoleHandler userConsoleHandler = AppFactory.getUserConsoleHandler();
        userConsoleHandler.start();


    }
}