package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Connessione;
import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.model.StatiDistributori;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.repository.ConnessioneRepository;
import it.unipa.wsda.progettocoffeecapp.repository.DistributoreRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class DistributoreService {

    private final ConnessioneRepository connessioneRepository;
    private final DistributoreRepository distributoreRepository;

    //RestClient integrato in Spring, serve per effettuare chiamate post (sincrone) direttamente nel backend
    //ho deciso volontariamente di utilizzare chiamate sincrone in quanto, per la mia interpretazione della consegna
    //i due servizi devono essere sempre attivi e l'impossibilità di sincronizzazione tra i due deve risultare in una impossibilità
    //di aggiornare il db in prima istanza. @Transactional mi garantisce che in caso di errore (anche fallimento della chiamata post)
    //venga fatto rollback completo delle modifiche che si volevano tentare. Per questo motivo ho inoltre strutturato gli errori/exception
    //in modo che vangano propagati fino al frontend e notificati all'utente
    private final RestClient restClient = RestClient.create();
    private final String URL = "http://localhost:8081/heartbeat_service_war_exploded/distributori"; //progetto Jakarta

    public DistributoreService(ConnessioneRepository connessioneRepository,
                              DistributoreRepository distributoreRepository) {
        this.connessioneRepository = connessioneRepository;
        this.distributoreRepository = distributoreRepository;
    }



    public Optional<Utente> getUtenteConnesso(String distributoreId) {
        return connessioneRepository.findByDistributoreId_distributore(distributoreId)
                .map(Connessione::getUtente);
    }

    public Optional<Distributore> getDistributoreById(String idDistributore) {
        return distributoreRepository.findById(idDistributore);
    }

    public Iterable<Distributore> getAllDistributori(){
        return distributoreRepository.findAll();
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
                throw new RuntimeException("Errore durante la sincronizzazione: " + e.getMessage());
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
        try {
            record recordDistributore(String id, StatiDistributori stato, Double lat, Double lon, Timestamp lastHeartbeat) {}
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
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la sincronizzazione: " + e.getMessage());
        }

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
            throw new RuntimeException("Errore durante la sincronizzazione: " + e.getMessage());
        }
    }

}
