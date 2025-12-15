import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306";
        String user = "root";
        String password = "1234567890";

        try {
            // 1) Apertura connessione
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connessione OK");

            // 2) Creazione statement
            Statement stmt = conn.createStatement();

            // 3) Prima query
            ResultSet rs = stmt.executeQuery("SELECT 1");

            // 4) Lettura risultato
            if (rs.next()) {
                int value = rs.getInt(1);
                System.out.println("Risultato query: " + value);
            }

            // 5) Chiusura risorse
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
