package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Connessione;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistributoreService {

    private final ConnessioneRepository connessioneRepository;

    public DistributoreService(ConnessioneRepository connessioneRepository) {
        this.connessioneRepository = connessioneRepository;
    }

    public Optional<Utente> getUtenteConnesso(String distributoreId) {
        return connessioneRepository.findByDistributoreId_distributoreAndDataFineIsNull(distributoreId)
                .map(Connessione::getUtente);
    }
}
