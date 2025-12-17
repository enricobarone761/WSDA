package it.unipa.wsda.heartbeat_service.d_DatabaseLayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class distributori_db {
    public static void main(String[] args) {

        try (Connection conn = conn_distributore.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1) Creazione database
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS distributori_info");
            System.out.println("Database creato");

            // 2) Seleziona il database appena creato
            stmt.executeUpdate("USE distributori_info");

            // 3) Creazione tabella 'distributori'
            String createTable = "CREATE TABLE IF NOT EXISTS distributori (" +
                    "id VARCHAR(50) PRIMARY KEY, " +
                    "stato VARCHAR(20) NOT NULL, " +
                    "lat DOUBLE, " +
                    "lon DOUBLE, " +
                    "last_heartbeat TIMESTAMP)";
            stmt.executeUpdate(createTable);
            System.out.println("Tabella creata");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
