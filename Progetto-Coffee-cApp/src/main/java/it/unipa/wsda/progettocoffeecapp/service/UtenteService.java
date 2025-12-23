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

    @Transactional
    public boolean scalaCredito(Integer idUtente, int costo) {
        Optional<Utente> utenteOpt = utenteRepository.findById(idUtente);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            if (utente.getCredito_residuo() >= costo) {
                utente.setCredito_residuo(utente.getCredito_residuo() - costo);
                //utenteRepository.save(utente);
                return true;
            }
        }
        return false;
    }
}
