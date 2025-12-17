package it.unipa.wsda.heartbeat_service.c_DataAccessLayer;

import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.conn_distributore;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributor;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.DistributorStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistributorDAO {

    public void save(Distributor d) throws SQLException {
        String sql = "INSERT INTO distributori (id, stato, lat, lon, last_heartbeat) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getId());
            ps.setString(2, d.getStatus().name());
            ps.setDouble(3, d.getLat());
            ps.setDouble(4, d.getLon());
            ps.setTimestamp(5, d.getLastHeartbeat());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM distributori WHERE id = ?";
        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public void updateStatus(String id, DistributorStatus status) throws SQLException {
        String sql = "UPDATE distributori SET stato = ? WHERE id = ?";
        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setString(2, id);
            ps.executeUpdate();
        }
    }

    public void updateHeartbeat(String id, Timestamp timestamp) throws SQLException {
        // When receiving heartbeat, we also set status to ATTIVO if it was GUASTO?
        // The requirements don't explicitly say, but usually a heartbeat means it's alive.
        // However, let's stick to just updating the timestamp. The service logic can decide if status needs update.
        // Actually, if it was GUASTO and sends a heartbeat, it should probably become ATTIVO.
        // But for now, let's just update the timestamp.
        String sql = "UPDATE distributori SET last_heartbeat = ? WHERE id = ?";
        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, timestamp);
            ps.setString(2, id);
            ps.executeUpdate();
        }
    }

    public List<Distributor> findAll() throws SQLException {
        List<Distributor> list = new ArrayList<>();
        String sql = "SELECT * FROM distributori";
        try (Connection conn = conn_distributore.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Distributor findById(String id) throws SQLException {
        String sql = "SELECT * FROM distributori WHERE id = ?";
        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // Updates status to GUASTO for distributors that haven't sent heartbeat since 'threshold'
    // AND are not in MANUTENZIONE.
    public int markBrokenDistributors(Timestamp threshold) throws SQLException {
        String sql = "UPDATE distributori SET stato = ? WHERE last_heartbeat < ? AND stato != ?";
        try (Connection conn = conn_distributore.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, DistributorStatus.GUASTO.name());
            ps.setTimestamp(2, threshold);
            ps.setString(3, DistributorStatus.MANUTENZIONE.name());
            return ps.executeUpdate();
        }
    }

    private Distributor mapRow(ResultSet rs) throws SQLException {
        return new Distributor(
                rs.getString("id"),
                DistributorStatus.valueOf(rs.getString("stato")),
                rs.getDouble("lat"),
                rs.getDouble("lon"),
                rs.getTimestamp("last_heartbeat")
        );
    }
}

