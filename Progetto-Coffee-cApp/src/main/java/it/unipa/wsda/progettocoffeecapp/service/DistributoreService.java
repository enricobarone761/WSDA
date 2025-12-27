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
        return connessioneRepository.findByDistributoreId_distributore(distributoreId)
                .map(Connessione::getUtente);
    }

    public Iterable<Distributore> getAllDistributori(){
        return distributoreRepository.findAll();
    }

    @Transactional
    public void connetti(Integer idUtente, String idDistributore) {
        // cancelliamo eventuali connessione attive precedenti, un utente supporta una connessione attiva alla volta
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

    @Transactional
    public void ripristinaForniture(String idDistributore) {
        distributoreRepository.findById(idDistributore).ifPresent(distributore -> {
            distributore.setLivello_caffe(100);
            distributore.setLivello_latte(100);
            distributore.setLivello_zucchero(100);
            distributore.setLivello_bicchieri(100);
            distributore.setLivello_cioccolato(100);
            distributore.setLivello_the(100);
            distributoreRepository.save(distributore);
        });
    }

    @Transactional
    public void ripristinaGuasti(String idDistributore) {
        distributoreRepository.findById(idDistributore).ifPresent(distributore -> {
            distributore.setStato_pompa_acqua(true);
            distributore.setStato_riscaldatore(true);
            distributore.setStato_erogatore(true);
            distributore.setStato_display(true);
            distributore.setStato_gettoniera(true);
            distributore.setStato_macina_caffe(true);
            distributoreRepository.save(distributore);
        });
    }

    @Transactional
    public void cambiaStato(String idDistributore, StatiDistributori nuovoStato) {
        distributoreRepository.findById(idDistributore).ifPresent(distributore -> {
            distributore.setStato(nuovoStato);
            distributoreRepository.save(distributore);
        });
    }
}
