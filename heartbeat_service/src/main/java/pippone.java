import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

void main() {

    String url = "jdbc:mysql://127.0.0.1:3306";
    String user = "root";
    String password = "1234567890"; // metti la tua password se ce l'hai

    try (Connection conn = DriverManager.getConnection(url, user, password);
         Statement stmt = conn.createStatement()) {

        // 1) Creazione database
        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS testdb");
        IO.println("Database creato");

        // 2) Seleziona il database appena creato
        stmt.executeUpdate("USE testdb");

        // 3) Creazione tabella 'caffe'
        String createTable = "CREATE TABLE IF NOT EXISTS caffe (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nome VARCHAR(50), " +
                "livello INT)";
        stmt.executeUpdate(createTable);
        IO.println("Tabella 'caffe' creata");

        // 4) Inserimento dati
        stmt.executeUpdate("INSERT INTO caffe (nome, livello) VALUES ('Arabica', 50)");
        stmt.executeUpdate("INSERT INTO caffe (nome, livello) VALUES ('Robusta', 70)");
        stmt.executeUpdate("INSERT INTO caffe (nome, livello) VALUES ('Liberica', 30)");
        IO.println("Dati inseriti");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
