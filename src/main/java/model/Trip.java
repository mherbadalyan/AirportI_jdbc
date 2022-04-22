package model;

import java.sql.Time;
import java.util.Objects;

public class Trip {
    private int tripId;
    private int compId;
    private String plane;
    private String townFrom;
    private String townTo;
    private Time timeOut;
    private Time timeIn;

    public Trip() {
    }
    public Trip(int tripId, int compId, String plane, String townFrom, String townTo, Time timeOut, Time timeIn) {
        this.tripId = tripId;
        this.compId = compId;
        this.plane = plane;
        this.townFrom = townFrom;
        this.townTo = townTo;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
    }
    public String getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(String townFrom) {
        this.townFrom = townFrom;
    }

    public String getTownTo() {
        return townTo;
    }

    public void setTownTo(String townTo) {
        this.townTo = townTo;
    }

    public Time getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Time timeOut) {
        this.timeOut = timeOut;
    }

    public Time getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Time timeIn) {
        this.timeIn = timeIn;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return tripId == trip.tripId && compId == trip.compId && Objects.equals(plane, trip.plane) && Objects.equals(townFrom, trip.townFrom) && Objects.equals(townTo, trip.townTo) && Objects.equals(timeOut, trip.timeOut) && Objects.equals(timeIn, trip.timeIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, compId, plane, townFrom, townTo, timeOut, timeIn);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "trip_id=" + tripId +
                ", comp_id=" + compId +
                ", plane='" + plane + '\'' +
                ", townFrom='" + townFrom + '\'' +
                ", townTo='" + townTo + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", timeIn='" + timeIn + '\'' +
                '}';
    }
}
