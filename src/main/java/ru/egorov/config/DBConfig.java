package ru.egorov.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class DBConfig {
    public static Connection getConnection() {
        Optional<Connection> optionalConnection = Optional.empty();
        try {
            Properties properties = ApplicationConfig.getProperties();
            String dbUrl = properties.getProperty("db.url");
            String dbUser = properties.getProperty("db.user");
            String dbPassword = properties.getProperty("db.password");

            optionalConnection = Optional.of(DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        } catch (SQLException e) {
            System.err.println("Ошибка в подключении к базе данных. Подробнее: " + e.getMessage());
            System.exit(1);
        }
        return optionalConnection.orElseThrow(() -> new RuntimeException("Неизвестная ошибка."));
    }

}
