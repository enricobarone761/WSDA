package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Connessione;
import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import it.unipa.wsda.progettocoffeecapp.repository.DistributoreRepository;
import it.unipa.wsda.progettocoffeecapp.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class DistributoreService {

    private final ConnessioneRepository connessioneRepository;
    private final UtenteRepository utenteRepository;
    private final DistributoreRepository distributoreRepository;

    public DistributoreService(ConnessioneRepository connessioneRepository, 
                              UtenteRepository utenteRepository, 
                              DistributoreRepository distributoreRepository) {
        this.connessioneRepository = connessioneRepository;
        this.utenteRepository = utenteRepository;
        this.distributoreRepository = distributoreRepository;
    }

    public Optional<Utente> getUtenteConnesso(String distributoreId) {
        return connessioneRepository.findByDistributoreId_distributoreAndDataFineIsNull(distributoreId)
                .map(Connessione::getUtente);
    }

    @Transactional
    public void connetti(Integer idUtente, String idDistributore) {
        // Rimuoviamo eventuali connessioni attive precedenti per questo utente o distributore
        // (dato che i campi sono unique nel modello)
        disconnetti(idUtente);
        
        Optional<Utente> utenteOpt = utenteRepository.findById(idUtente);
        Optional<Distributore> distributoreOpt = distributoreRepository.findById(idDistributore);

        if (utenteOpt.isPresent() && distributoreOpt.isPresent()) {
            Connessione connessione = new Connessione();
            connessione.setUtente(utenteOpt.get());
            connessione.setDistributore(distributoreOpt.get());
            connessioneRepository.save(connessione);
        }
    }

    @Transactional
    public void disconnetti(Integer idUtente) {
        connessioneRepository.findByUtenteId(idUtente).ifPresent(connessioneRepository::delete);
    }
}
