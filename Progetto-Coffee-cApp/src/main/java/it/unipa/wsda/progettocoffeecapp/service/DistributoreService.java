package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Connessione;
import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.model.StatiDistributori;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import it.unipa.wsda.progettocoffeecapp.repository.DistributoreRepository;
import it.unipa.wsda.progettocoffeecapp.repository.UtenteRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.Optional;

@Service
public class DistributoreService {

    private final ConnessioneRepository connessioneRepository;
    private final UtenteRepository utenteRepository;
    private final DistributoreRepository distributoreRepository;

    //RestClient integrato in Spring, serve per effettuare chiamate post (sincrone) direttamente nel backend
    private final RestClient restClient = RestClient.create();
    private final String URL = "http://localhost:8081/heartbeat_service_war_exploded/distributori"; //progetto Jakarta

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

    @Transactional
    public void ripristinaForniture(String idDistributore) {
        distributoreRepository.findById(idDistributore).ifPresent(this::ripristinaFornitureDistributore);
    }
    private void ripristinaFornitureDistributore(Distributore distributore) {
        distributore.setLivello_caffe(100);
        distributore.setLivello_latte(100);
        distributore.setLivello_zucchero(100);
        distributore.setLivello_bicchieri(100);
        distributore.setLivello_cioccolato(100);
        distributore.setLivello_the(100);
        distributoreRepository.save(distributore);
    }

    @Transactional
    public void ripristinaGuasti(String idDistributore) {
        distributoreRepository.findById(idDistributore).ifPresent(this::ripristinaGuastiDistributore);
    }
    private void ripristinaGuastiDistributore(Distributore distributore) {
        distributore.setStato_pompa_acqua(true);
        distributore.setStato_riscaldatore(true);
        distributore.setStato_erogatore(true);
        distributore.setStato_display(true);
        distributore.setStato_gettoniera(true);
        distributore.setStato_macina_caffe(true);
        distributoreRepository.save(distributore);
    }

    @Transactional
    public void cambiaStato(String idDistributore, StatiDistributori nuovoStato, boolean sync) {
        distributoreRepository.findById(idDistributore).ifPresent(distributore -> {
            distributore.setStato(nuovoStato);
            distributoreRepository.save(distributore);
        });

        //sync con db Jakarta
        if (sync) { //il controllo sync mi è necessario per evitare loop nella sincronizzazione tra i due db dell'assignment
            try {
                restClient.put()
                        .uri(URL +"/status"+ "?id=" + idDistributore + "&stato=" + nuovoStato)
                        .retrieve()
                        .toBodilessEntity();

                System.out.println("Cambio stato inviato");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional
    public void saveDistributore(Distributore distributore) {
        if (distributore.getStato() == null) {
            distributore.setStato(StatiDistributori.INATTIVO);
        }

        // quando salvo un nuovo distributore questo viene di default impostato con tutto al massimo
        ripristinaFornitureDistributore(distributore);
        ripristinaGuastiDistributore(distributore);

        //Sistema per ottenere coordinate lon e lat automaticamente dalla via
        //ho trovato su internet questa API gratuita che per il nostro scopo è piu che ottima
        try {
            JsonNode root = restClient.get()
                    .uri("https://geocode.maps.co/search?q={indirizzo}&api_key=695960396263f379070428vux46ce69", distributore.getVia())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(JsonNode.class);

            if (root != null) {
                final JsonNode primoRisultato = root.get(0);
                distributore.setLat( primoRisultato.get("lat").asDouble() );
                distributore.setLon( primoRisultato.get("lon").asDouble() );
            }

        } catch (Exception e) {
            System.err.println("Valore coordinate non trovato");
            distributore.setLat(null);
            distributore.setLon(null);

        } finally {
            distributoreRepository.save(distributore);
        }



        //sync con db Jakarta
        record recordDistributore(String id, StatiDistributori stato, Double lat, Double lon, Object lastHeartbeat) {}
        restClient.post()
                .uri(URL)
                .body(new recordDistributore(
                        distributore.getId_distributore(),
                        distributore.getStato(),
                        distributore.getLat(),
                        distributore.getLon(),
                        null
                ))
                .retrieve()
                .toBodilessEntity();

    }

    @Transactional
    public void deleteDistributoreById(String id) {
        distributoreRepository.deleteById(id);

        //sync con db Jakarta
        try {
            restClient.delete()
                    .uri(URL + "?id=" + id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
