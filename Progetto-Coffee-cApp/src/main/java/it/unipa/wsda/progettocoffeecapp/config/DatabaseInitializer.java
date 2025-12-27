package it.unipa.wsda.progettocoffeecapp.config;

import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner clearConnessioneTable(ConnessioneRepository connessioneRepository) {
        return args -> {
            connessioneRepository.deleteAll();
            System.out.println("Tabella Connessione pulita con successo all'avvio dell'applicazione.");
        };
    }
}
