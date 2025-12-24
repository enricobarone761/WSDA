package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public Optional<Utente> findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    public Optional<Utente> findById(Integer id) {
        return utenteRepository.findById(id);
    }

    @Transactional
    public Utente registraNuovoUtente(String nome, String cognome, String email, String password) throws Exception {
        // Verifica se l'utente esiste già
        if (utenteRepository.findByUsername(email).isPresent()) {
            throw new Exception("Utente già registrato con questa email.");
        }

        // Creazione nuovo utente
        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setUsername(email);
        nuovoUtente.setPassword(password); // In un caso reale, la password andrebbe criptata
        nuovoUtente.setCredito_residuo(0);
        nuovoUtente.setRuolo("CLIENTE");

        return utenteRepository.save(nuovoUtente);
    }

    @Transactional
    public boolean scalaCredito(Integer idUtente, int costo) {
        Optional<Utente> utenteOpt = utenteRepository.findById(idUtente);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            if (utente.getCredito_residuo() >= costo) {
                utente.setCredito_residuo(utente.getCredito_residuo() - costo);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void ricaricaCredito(Integer idUtente, int importo) {
        Optional<Utente> utenteOpt = utenteRepository.findById(idUtente);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            utente.setCredito_residuo(utente.getCredito_residuo() + importo);
            utenteRepository.save(utente);
        }
    }
}
