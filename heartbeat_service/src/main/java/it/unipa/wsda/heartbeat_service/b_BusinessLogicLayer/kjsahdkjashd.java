/*
package it.unipa.wsda.heartbeat_service.b_BusinessLogicLayer;

import it.unipa.wsda.heartbeat_service.c_DataAccessLayer.DistributorDAO;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.Distributor;
import it.unipa.wsda.heartbeat_service.d_DatabaseLayer.StatiDistributori;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class kjsahdkjashd {

    private final DistributorDAO dao;
    private static long lastBrokenCheckTime = 0;
    private static final long CHECK_INTERVAL_MS = 60000; // Check every 1 minute
    private static final long HEARTBEAT_TIMEOUT_MS = 3 * 60 * 1000; // 3 minutes

    public kjsahdkjashd() {
        this.dao = new DistributorDAO();
    }

    public void addDistributor(Distributor d) throws SQLException {
        // Default status if not provided
        if (d.getStatus() == null) {
            d.setStatus(StatiDistributori.ATTIVO);
        }
        if (d.getLastHeartbeat() == null) {
            d.setLastHeartbeat(Timestamp.from(Instant.now()));
        }
        dao.save(d);
    }

    public void removeDistributor(String id) throws SQLException {
        dao.delete(id);
    }

    public void updateStatus(String id, StatiDistributori status) throws SQLException {
        dao.updateStatus(id, status);
    }

    public void receiveHeartbeat(String id) throws SQLException {
        Timestamp now = Timestamp.from(Instant.now());
        dao.updateHeartbeat(id, now);

        // If it was broken, maybe we should set it to active?
        // The requirements don't explicitly say "auto-recover", but it makes sense.
        // However, strictly following requirements: "mark as broken... if no heartbeat".
        // It doesn't say "mark as active if heartbeat returns".
        // But usually a heartbeat implies activity. Let's check if it is GUASTO, set to ATTIVO.
        Distributor d = dao.findById(id);
        if (d != null && d.getStatus() == StatiDistributori.GUASTO) {
            dao.updateStatus(id, StatiDistributori.ATTIVO);
        }
    }

    public List<Distributor> getAllDistributors() throws SQLException {
        long now = System.currentTimeMillis();

        // Optimization: Check for broken distributors only if enough time has passed
        if (now - lastBrokenCheckTime > CHECK_INTERVAL_MS) {
            Timestamp threshold = new Timestamp(now - HEARTBEAT_TIMEOUT_MS);
            dao.markBrokenDistributors(threshold);
            lastBrokenCheckTime = now;
        }

        return dao.findAll();
    }
}

*/
