package dao;

import model.Trip;

import java.util.List;
import java.util.Set;

public interface TripDao {
    Trip getById(int id);
    Set<Trip> getAll();
    Set<Trip> get(int offset, int perPage, String sort);
    boolean save(Trip passenger);
    boolean update(Trip passenger);
    boolean delete(int tripId);
    List<Trip> getTripsFrom(String city);
    List<Trip> getTripsTo(String city);

}
