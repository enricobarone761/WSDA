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
        // verifica se l'utente esiste già
        if (utenteRepository.findByUsername(email).isPresent()) {
            throw new Exception("Email già esistente");
        }

        // Creazione nuovo utente
        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setUsername(email);
        nuovoUtente.setPassword(password);
        nuovoUtente.setCredito_residuo(0.0);
        nuovoUtente.setRuolo("CLIENTE");

        return utenteRepository.save(nuovoUtente);
    }

    @Transactional
    public boolean scalaCredito(Integer idUtente, double costo) {
        Optional<Utente> utenteOpt = utenteRepository.findById(idUtente);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            if (utente.getCredito_residuo() >= costo) {
                utente.setCredito_residuo(utente.getCredito_residuo() - costo);
                utenteRepository.save(utente);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean ricaricaCredito(Integer idUtente, double importo) {
        Optional<Utente> utenteOpt = utenteRepository.findById(idUtente);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            utente.setCredito_residuo(utente.getCredito_residuo() + importo);
            utenteRepository.save(utente);
            return true;
        }
        return false;
    }
}
