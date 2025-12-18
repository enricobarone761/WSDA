package it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer;

import it.unipa.wsda.heartbeat_service.c_DataAccessLayer.DistributoreDAO;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.StatiDistributori;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributore;
import jakarta.ejb.Schedule;

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

    @Schedule(hour = "*", minute = "*/10", persistent = false)
    public void checkDatabase() {
        try {
            var lista_distributori = allDistributori();
            lista_distributori.forEach(dis->{
                if(System.currentTimeMillis() - dis.getLastHeartbeat().getTime() > 3 * 60 * 1000){
                    try {
                        aggiornaStato(dis.getId() , StatiDistributori.GUASTO);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
