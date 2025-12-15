package unipa.it.progetto_coffeecapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esempio di connessione al database MySQL utilizzando JDBC.
 * 
 * Per utilizzare questa classe, assicurati di:
 * 1. Avere MySQL installato e in esecuzione
 * 2. Creare un database chiamato "nome_db" (o modificare il nome nel codice)
 * 3. Configurare username e password corretti
 */
public class DatabaseConnectionExample {
    
    // ATTENZIONE: Questi sono valori di esempio per scopo didattico.
    // In un'applicazione reale, NON inserire mai credenziali hardcoded nel codice!
    // Utilizzare invece:
    // - Variabili d'ambiente
    // - File application.properties di Spring Boot
    // - Gestori di configurazione sicuri
    
    // Parametri di connessione - MODIFICARE in base alla propria configurazione
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/nome_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    
    public static void main(String[] args) {
        Connection connection = null;
        
        try {
            // Tentativo di connessione al database
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✓ Connessione al database MySQL riuscita!");
            System.out.println("Database: " + connection.getCatalog());
            
        } catch (SQLException e) {
            System.err.println("✗ Errore durante la connessione al database:");
            System.err.println("Messaggio: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Codice errore: " + e.getErrorCode());
            e.printStackTrace();
            
        } finally {
            // Chiusura della connessione
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("✓ Connessione chiusa correttamente.");
                } catch (SQLException e) {
                    System.err.println("✗ Errore durante la chiusura della connessione:");
                    e.printStackTrace();
                }
            }
        }
    }
}
