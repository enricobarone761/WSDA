package it.unipa.wsda.progettocoffeecapp.config;

import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//questa classe svuota la tabella Connessione ad ogni avvio di spring TODO

@Configuration
public class SvuotaTabellaConnessioneAvvio {

    @Bean
    CommandLineRunner clearConnessioneTable(ConnessioneRepository connessioneRepository) {
        return _ -> {
            connessioneRepository.deleteAll();
            System.out.println("Tabella Connessione svuotata");
        };
    }
}
