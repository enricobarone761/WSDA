package it.unipa.wsda.heartbeat_service.d_DatabaseLayer;

import java.sql.Timestamp;

public class distributore {
    private String id;
    private DistributorStatus status;
    private double lat;
    private double lon;
    private Timestamp lastHeartbeat;

    public distributore(String id, DistributorStatus status, double lat, double lon, Timestamp lastHeartbeat) {
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

    public DistributorStatus getStatus() {
        return status;
    }

    public void setStatus(DistributorStatus status) {
        this.status = status;
    }
}
