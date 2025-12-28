package it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer;

import it.unipa.wsda.heartbeat_service.c_DataAccessLayer.DistributoreDAO;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.StatiDistributori;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributore;

import java.sql.SQLException;
import java.util.List;


public class DistributoreService {

    private final DistributoreDAO dao = new DistributoreDAO();

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

        for (Distributore dis : allDistributori()) {
            // Controllo se è passato troppo tempo E se NON è in manutenzione
            if (dis.getStatus() != StatiDistributori.MANUTENZIONE &&
                    (adesso - dis.getLastHeartbeat().getTime() > tempoLimite)) {

                // aggiorna solo se non è già GUASTO per evitare query inutili
                if (dis.getStatus() != StatiDistributori.GUASTO) {
                    aggiornaStato(dis.getId(), StatiDistributori.GUASTO);
                }
            }
        }

        //TODO qui deve essere fatto la POST per sincronizzare i db

    }

}
