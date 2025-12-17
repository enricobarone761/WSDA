package it.unipa.wsda.heartbeat_service.d_DatabaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDistributore {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/distributori_info";
    private static final String USER = "root";
    private static final String PASSWORD = "1234567890";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

