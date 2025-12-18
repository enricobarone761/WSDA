package it.unipa.wsda.heartbeat_service.d_DatabaseLayer;

import java.sql.Timestamp;

public class Distributore {
    private String id;
    private StatiDistributori status;
    private double lat;
    private double lon;
    private Timestamp lastHeartbeat;

    public Distributore(String id, StatiDistributori status, double lat, double lon, Timestamp lastHeartbeat) {
        this.id = id;
        this.status = status;
        this.lat = lat;
        this.lon = lon;
        this.lastHeartbeat = lastHeartbeat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Timestamp getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(Timestamp lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public StatiDistributori getStatus() { return status; }

    public void setStatus(StatiDistributori status) {
        this.status = status;
    }
}
