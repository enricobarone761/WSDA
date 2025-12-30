package it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer;

import it.unipa.wsda.heartbeat_service.c_DataAccessLayer.DistributoreRepository;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.StatiDistributori;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributore;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;



public class DistributoreService {

    private final DistributoreRepository dao = new DistributoreRepository();

    public void aggiungiDistributore(Distributore dis) throws SQLException {
        dao.save(dis);
    }

    public void rimuoviDistributore(String id) throws SQLException{
        dao.remove(id);
    }

    public void aggiornaStato(String id, StatiDistributori ds) throws SQLException{
        dao.updateStatus(id, ds);
    }

    public List<Distributore> allDistributori() throws SQLException {
        return dao.findAll();
    }

    public void aggiornaUltimoHeartbeat(String id) throws SQLException{
        dao.updateLastHeartbeat(id);
    }

    public void aggiornaStatoDistributoriGuasti() throws SQLException {
        long tempoLimite = 3 * 60 * 1000L; // 3 minuti
        long adesso = System.currentTimeMillis();
        Set<String> lista_id_distributori_guasti = new HashSet<>();

        for (Distributore dis : allDistributori()) {
            // Controllo se è passato troppo tempo E se NON è in manutenzione o inattivo
            if (dis.getStato() != StatiDistributori.MANUTENZIONE &&
                    dis.getStato() != StatiDistributori.INATTIVO &&
                    (adesso - dis.getLastHeartbeat().getTime() > tempoLimite)) {

                lista_id_distributori_guasti.add(dis.getId()); //lista id da inviare

                // aggiorna solo se non è già GUASTO per evitare query inutili
                if (dis.getStato() != StatiDistributori.GUASTO) {
                    aggiornaStato(dis.getId(), StatiDistributori.GUASTO);
                }
            }
        }

        //sync del db con il progetto Spring dei distributori con guasto, dall'altro lato un endpoint legge la lista di id che invio
        try (Client client = ClientBuilder.newClient()) {
            Response response = client
                    .target("http://localhost:8080/sync")
                    .request()
                    .post(Entity.json(lista_id_distributori_guasti));
            response.close();
        } finally {
            System.out.println("Aggiornamento inviato");
        }
    }

}
