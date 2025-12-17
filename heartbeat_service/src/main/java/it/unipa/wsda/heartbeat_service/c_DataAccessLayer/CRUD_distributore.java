package it.unipa.wsda.heartbeat_service.c_DataAccessLayer;

import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.DistributorStatus;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.conn_distributore;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.distributore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUD_distributore {

    //CREATE
    public void save(distributore dis) throws SQLException {
        String sql = "INSERT INTO distributori(id,stato,lat,lon,last_heartbeat) VALUES (?,?,?,?,?)";

        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dis.getId());
            ps.setString(2,dis.getStatus().toString());
            ps.setDouble(3,dis.getLat());
            ps.setDouble(4,dis.getLon());
            ps.setTimestamp(5, dis.getLastHeartbeat());

            ps.executeUpdate();

        }
    }

    //READ

    //UPDATE
    // con questa funzione possiamo attivare/disattivare/mettere in manutenzione
    public void updateStatus(String id, DistributorStatus ds) throws SQLException {
        String sql = "UPDATE distributori SET stato = ? WHERE id = ?";

        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ds.toString());
            ps.setString(2,id);
        }
    }



    //DELETE
    public void remove(String id) throws SQLException {
        String sql = "DELETE FROM distributori WHERE id = ?";

        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,id);

        }
    }
}
