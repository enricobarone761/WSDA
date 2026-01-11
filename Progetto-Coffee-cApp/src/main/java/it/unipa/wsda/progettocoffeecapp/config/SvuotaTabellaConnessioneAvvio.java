package it.unipa.wsda.progettocoffeecapp.config;

import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//questa classe svuota la tabella Connessione ad ogni avvio di spring
//per evitare problemi di sincronizzazione con il frontend in caso di riavvio TODO

@Configuration
public class SvuotaTabellaConnessioneAvvio {

    @Bean
    CommandLineRunner svuotaTabellaConnessione(ConnessioneRepository connessioneRepository) {
        return e -> {
            connessioneRepository.deleteAll();
            System.out.println("Tabella Connessione svuotata");
        };
    }
}
