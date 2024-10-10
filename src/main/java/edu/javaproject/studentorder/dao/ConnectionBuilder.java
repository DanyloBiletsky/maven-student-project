package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static edu.javaproject.studentorder.config.Config.*;

public class ConnectionBuilder {
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                Config.getProperty(DB_URL),
                Config.getProperty(DB_LOGIN),
                Config.getProperty(DB_PASSWORD));
        return connection;
    }
}
