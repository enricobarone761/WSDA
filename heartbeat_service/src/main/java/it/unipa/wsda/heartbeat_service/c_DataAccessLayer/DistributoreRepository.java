package it.unipa.wsda.heartbeat_service.c_DataAccessLayer;

import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.StatiDistributori;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.ConnessioneDistributore;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistributoreRepository {

    //CREATE
    public void save(Distributore dis) throws SQLException {
        String sql = "INSERT INTO distributori(id,stato,lat,lon,last_heartbeat) VALUES (?,?,?,?,?)";

        try (Connection conn = ConnessioneDistributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dis.getId());
            ps.setString(2,dis.getStato().toString());
            ps.setDouble(3,dis.getLat());
            ps.setDouble(4,dis.getLon());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

        }
    }

    //READ
    public List<Distributore> findAll() throws SQLException {
        String sql = "SELECT * FROM distributori";

        try (Connection conn = ConnessioneDistributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet ris = ps.executeQuery()) {

            List<Distributore> lista_distributori_trovati = new ArrayList<>();
            while (ris.next()){
                lista_distributori_trovati.add(mapRow(ris));
            }
            return lista_distributori_trovati;
        }
    }

    public Optional<Distributore> findById(String id) throws SQLException {
        String sql = "SELECT * FROM distributori WHERE id = ?";

        try (Connection conn = ConnessioneDistributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ) {

            ps.setString(1, id);
            try (ResultSet ris = ps.executeQuery()){
                if (ris.next()){
                    return Optional.of(mapRow(ris));
                }
            }

        }
        return Optional.empty();
    }

    private Distributore mapRow(ResultSet rs) throws SQLException {
        return new Distributore(
                rs.getString("id"),
                StatiDistributori.valueOf(rs.getString("stato")),
                rs.getDouble("lat"),
                rs.getDouble("lon"),
                rs.getTimestamp("last_heartbeat")
        );
    }

    //UPDATE
    // con questa funzione possiamo attivare/disattivare/mettere in manutenzione
    public void updateStatus(String id, StatiDistributori ds) throws SQLException {
        String sql = "UPDATE distributori SET stato = ? WHERE id = ?";

        try (Connection conn = ConnessioneDistributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ds.toString());
            ps.setString(2,id);
            ps.executeUpdate();
        }
    }

    public void updateLastHeartbeat(String id) throws SQLException {
        String sql = "UPDATE distributori SET last_heartbeat = ? WHERE id = ?";

        try (Connection conn = ConnessioneDistributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            ps.setString(2,id);
            if (ps.executeUpdate() == 0) {
                // inserito per testare la servlet
                throw new SQLException("NotFound: distributore con id=" + id);
            }
        }
    }



    //DELETE
    public void remove(String id) throws SQLException {
        String sql = "DELETE FROM distributori WHERE id = ?";

        try (Connection conn = ConnessioneDistributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,id);
            ps.executeUpdate();

        }
    }
}
