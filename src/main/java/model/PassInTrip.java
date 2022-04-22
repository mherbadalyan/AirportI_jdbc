package model;

import java.sql.Date;
import java.util.Objects;

public class PassInTrip {
    private int tripId;
    private int psgId;
    private String place;
    private Date date;

    public PassInTrip() {
    }
    public PassInTrip(int tripId, int psgId, String place, Date date) {
        this.tripId = tripId;
        this.psgId = psgId;
        this.place = place;
        this.date = date;
    }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    public int getPsgId() {
        return psgId;
    }

    public void setPsgId(int psgId) {
        this.psgId = psgId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassInTrip that = (PassInTrip) o;
        return tripId == that.tripId && psgId == that.psgId && Objects.equals(place, that.place) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, psgId, place, date);
    }

    @Override
    public String toString() {
        return "PassInTrip{" +
                "trip_id=" + tripId +
                ", psg_id=" + psgId +
                ", place='" + place + '\'' +
                ", date=" + date +
                '}';
    }
}

