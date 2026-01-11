package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Connessione;
import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.model.StatiDistributori;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import it.unipa.wsda.progettocoffeecapp.repository.DistributoreRepository;
import it.unipa.wsda.progettocoffeecapp.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConnessioneService {

    private final ConnessioneRepository connessioneRepository;
    private final UtenteRepository utenteRepository;
    private final DistributoreRepository distributoreRepository;

    public ConnessioneService(ConnessioneRepository connessioneRepository,
                              UtenteRepository utenteRepository,
                              DistributoreRepository distributoreRepository) {
        this.connessioneRepository = connessioneRepository;
        this.utenteRepository = utenteRepository;
        this.distributoreRepository = distributoreRepository;
    }

    @Transactional
    public void connetti(Integer idUtente, String idDistributore) {
        // verifica che l'utente non sia gia connesso
        Optional<Connessione> connessioneEsistenteUtente = connessioneRepository.findByUtenteId(idUtente);
        if (connessioneEsistenteUtente.isPresent()) {
            throw new IllegalStateException("L'utente è già connesso ad un distributore");
        }

        // verifica che il distributore sia disponibile
        Optional<Connessione> connessioneEsistenteDistributore = connessioneRepository.findByDistributoreId_distributore(idDistributore);
        if (connessioneEsistenteDistributore.isPresent()) {
            throw new IllegalStateException("Il distributore è già occupato da un altro utente");
        }

        // verifica che l'utente esista
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        // verifica che il distributore esista
        Distributore distributore = distributoreRepository.findById(idDistributore)
                .orElseThrow(() -> new IllegalArgumentException("Distributore non trovato"));

        // verifica che il distributore sia attivo
        if (distributore.getStato() != StatiDistributori.ATTIVO) {
            throw new IllegalStateException("Il distributore non è attivo (stato: " + distributore.getStato() + ")");
        }

        // nuova connessione
        Connessione nuovaConnessione = new Connessione();
        nuovaConnessione.setUtente(utente);
        nuovaConnessione.setDistributore(distributore);
        connessioneRepository.save(nuovaConnessione);
    }

    @Transactional
    public void disconnetti(Integer idUtente) {
        connessioneRepository.findByUtenteId(idUtente).ifPresent(connessioneRepository::delete);
    }
}
