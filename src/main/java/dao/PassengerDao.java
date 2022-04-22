package dao;

import model.*;

import java.util.List;
import java.util.Set;

public interface PassengerDao {
    Passenger getById(int id);
    Set<Passenger> getAll();
    Set<Passenger> get(int offset, int perPage, String sort);
    boolean save(Passenger passenger);
    boolean update(Passenger passenger,int psgId);
    void delete(int passengerId);
    List<Passenger> getPassengersOfTrip(int tripNumber);
    boolean registerTrip(PassInTrip passInTrip);
    void cancelTrip(int passengerId, int tripNumber);

}
