package dao.impl;

import dao.TripDao;
import model.Address;
import model.Passenger;
import model.Trip;
import service.DBConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TripDaoImpl implements TripDao {
    @Override
    public Trip getById(int id) {
        Trip trip = null;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from trip where trip_id = ? "
             )
        ) {

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                trip = new Trip(
                        id,
                        rs.getInt("comp_id"),
                        rs.getString("plane"),
                        rs.getString("town_from"),
                        rs.getString("town_to"),
                        rs.getTime("time_out"),
                        rs.getTime("time_in")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trip;
    }

    @Override
    public Set<Trip> getAll() {
        Set<Trip> trips = new HashSet<>();
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from trip"
             )
        ) {
            ResultSet rs = preparedStatement.executeQuery();
            Trip trip;
            while (rs.next()) {
                trip = new Trip(
                        rs.getInt("trip_id"),
                        rs.getInt("comp_id"),
                        rs.getString("plane"),
                        rs.getString("town_from"),
                        rs.getString("town_to"),
                        rs.getTime("time_out"),
                        rs.getTime("time_in")
                );

                trips.add(trip);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trips;
    }

    @Override
    public Set<Trip> get(int offset, int perPage, String sort) {
        Set<Trip> set = null;
        Trip trip;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from trip " +
                             "order by " + sort + " limit ? offset ? ;"
             )
        ) {
            preparedStatement.setInt(1, perPage);
            preparedStatement.setInt(2, offset);


            ResultSet rs = preparedStatement.executeQuery();
            set = new LinkedHashSet<>();
            while (rs.next()) {
                trip = new Trip(
                        rs.getInt("trip_id"),
                        rs.getInt("comp_id"),
                        rs.getString("plane"),
                        rs.getString("town_from"),
                        rs.getString("town_to"),
                        rs.getTime("time_out"),
                        rs.getTime("time_in")
                );

                set.add(trip);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return set;
    }

    @Override
    public boolean save(Trip trip) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO trip (trip_id,comp_id,plane,town_from,town_to,time_out,time_in) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)"
             )
        ) {

            preparedStatement.setInt(1, trip.getTripId());
            preparedStatement.setInt(2, trip.getCompId());
            preparedStatement.setString(3, trip.getPlane());
            preparedStatement.setString(4, trip.getTownFrom());
            preparedStatement.setString(5, trip.getTownTo());
            preparedStatement.setTime(6, trip.getTimeOut());
            preparedStatement.setTime(7, trip.getTimeIn());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Trip trip) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE trip SET town_from = ?," +
                             "town_to = ?," +
                             "time_out = ?," +
                             "time_in = ?," +
                             "plane = ? " +
                             "where trip_id like " + trip.getTripId()
             )) {
            preparedStatement.setString(1, trip.getTownFrom());
            preparedStatement.setString(2, trip.getTownTo());
            preparedStatement.setTime(3, trip.getTimeOut());
            preparedStatement.setTime(4, trip.getTimeIn());
            preparedStatement.setString(5, trip.getPlane());

            preparedStatement.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int tripId) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from trip where trip_id = ? "
             )
        ) {
            preparedStatement.setInt(1, tripId);

            preparedStatement.execute();

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public List<Trip> getTripsFrom(String city) {
        List<Trip> list = null;
        Trip trip;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from trip " +
                             "where town_from like ?"
             )
        ) {
            preparedStatement.setString(1, city);
            ResultSet rs = preparedStatement.executeQuery();

            list = new ArrayList<>();
            while (rs.next()) {
                trip = new Trip(
                        rs.getInt("trip_id"),
                        rs.getInt("comp_id"),
                        rs.getString("plane"),
                        rs.getString("town_from"),
                        rs.getString("town_to"),
                        rs.getTime("time_out"),
                        rs.getTime("time_in")
                );
                list.add(trip);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Trip> getTripsTo(String city) {
        List<Trip> list = null;
        Trip trip;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from trip " +
                             "where town_to like ?"
             )
        ) {
            preparedStatement.setString(1, city);
            ResultSet rs = preparedStatement.executeQuery();

            list = new ArrayList<>();
            while (rs.next()) {
                trip = new Trip(
                        rs.getInt("trip_id"),
                        rs.getInt("comp_id"),
                        rs.getString("plane"),
                        rs.getString("town_from"),
                        rs.getString("town_to"),
                        rs.getTime("time_out"),
                        rs.getTime("time_in")
                );
                list.add(trip);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
