package it.unipa.wsda.heartbeat_service.d_DatabaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDistributore {
//    private static final String URL = "jdbc:mysql://127.0.0.1:3306/distributori_info";
//    private static final String USER = "root";
//    private static final String PASSWORD = "1234567890";

    private static final String URL = "jdbc:mysql://coffee-capp-132184a6-coffee-capp-0176.g.aivencloud.com:15746/distributori_info";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_OuZduaX3vxn1_XlG49y";

    public static Connection getConnection() throws SQLException {

        //il blocco che segue è necessario per iniettare correttamente la dipendenza del driver di connessione al db
        //senza questo non riesco a connettermi a mySql
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL non trovato", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

