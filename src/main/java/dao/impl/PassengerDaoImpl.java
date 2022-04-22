package dao.impl;

import dao.PassengerDao;
import model.*;
import service.DBConnectionService;

import java.sql.*;
import java.util.*;

public class PassengerDaoImpl implements PassengerDao {
    @Override
    public Passenger getById(int id) {
        Passenger passenger = null;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from passenger where psg_id = ? "
             )
        ) {

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                passenger = new Passenger();
                passenger.setName(rs.getString("psg_name"));
                passenger.setPhone(rs.getString("psg_phone"));
                passenger.setPassId(rs.getInt("psg_id"));
                Address address = getAddressById(rs.getInt("adr_id"));
                passenger.setAddress(address);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return passenger;
    }

    @Override
    public Set<Passenger> getAll() {
        Set<Passenger> passengerSet = new HashSet<>();
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from airport_jdbc.passenger " +
                             "inner join psg_address pa on passenger.adr_id = pa.adr_id"
             )
        ) {
            ResultSet rs = preparedStatement.executeQuery();
            Passenger passenger;
            while (rs.next()) {
                passenger = new Passenger(
                        rs.getString("psg_name"),
                        rs.getString("psg_phone"),
                        new Address(
                                rs.getString("country"),
                                rs.getString("city")
                        )
                );
                passenger.setPassId(rs.getInt("psg_id"));
                passengerSet.add(passenger);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return passengerSet;
    }

    @Override
    public Set<Passenger> get(int offset, int perPage, String sort) {
        Set<Passenger> passengerSet = null;
        Passenger passenger;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from airport_jdbc.passenger inner join psg_address pa on passenger.adr_id = pa.adr_id " +
                             "order by " + sort + " limit ? offset ? ;"
             )
        ) {

            preparedStatement.setInt(1, perPage);
            preparedStatement.setInt(2, offset);


            ResultSet rs = preparedStatement.executeQuery();
            passengerSet = new LinkedHashSet<>();
            while (rs.next()) {
                passenger = new Passenger(
                        rs.getString("psg_name"),
                        rs.getString("psg_phone"),
                        new Address(
                                rs.getString("country"),
                                rs.getString("city")
                        )
                );
                passenger.setPassId(rs.getInt("psg_id"));
                passengerSet.add(passenger);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return passengerSet;
    }

    @Override
    public boolean save(Passenger passenger) {
        if (passenger.getAddress().getAdrId() == 0) {
            saveAddress(passenger.getAddress());
            passenger.getAddress().setAdrId(getAddressId(passenger.getAddress()));
        }
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO passenger (psg_name,psg_phone,adr_id) " +
                             "VALUES (?, ?, ?)"
             )
        ) {

            preparedStatement.setString(1, passenger.getName());
            preparedStatement.setString(2, passenger.getPhone());
            preparedStatement.setInt(3, passenger.getAddress().getAdrId());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }
    @Override
    public boolean update(Passenger passenger, int psgId) {
        updateAddress(passenger.getAddress(),passenger.getAddress().getAdrId());
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE passenger " +
                             "SET psg_name = ?," +
                             "psg_phone = ?" +
                             "where psg_id = ?; "
             )
        ) {

            preparedStatement.setString(1, passenger.getName());
            preparedStatement.setString(2, passenger.getPhone());
            preparedStatement.setInt(3, psgId);

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public void delete(int passengerId) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from passenger where psg_id = ? "
             )
        ) {
            preparedStatement.setInt(1, passengerId);

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Passenger> getPassengersOfTrip(int tripNumber) {
        List<Passenger> list = null;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from airport_jdbc.pass_in_trip " +
                             "inner join passenger p on pass_in_trip.psg_id = p.psg_id " +
                             "inner join psg_address on p.adr_id = psg_address.adr_id " +
                             "where pass_in_trip.trip_id like ?"
             )
        ) {
            preparedStatement.setInt(1, tripNumber);
            ResultSet rs = preparedStatement.executeQuery();
            Passenger passenger;
            list = new ArrayList<>();
            while (rs.next()) {
                passenger = new Passenger(
                        rs.getString("psg_name"),
                        rs.getString("psg_phone"),
                        new Address(
                                rs.getString("country"),
                                rs.getString("city")
                        )
                );
                passenger.setPassId(rs.getInt("psg_id"));
                list.add(passenger);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    @Override
    public boolean registerTrip(PassInTrip passInTrip) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO pass_in_trip (trip_id,psg_id,date,place) " +
                             "VALUES (?, ?, ?, ?)"
             )
        ) {
            preparedStatement.setInt(1, passInTrip.getTripId());
            preparedStatement.setInt(2, passInTrip.getPsgId());
            preparedStatement.setDate(3, passInTrip.getDate());
            preparedStatement.setString(4, passInTrip.getPlace());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            return false;
        }

        return true;
    }
    @Override
    public void cancelTrip(int passengerId, int tripNumber) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from pass_in_trip where psg_id = ? and trip_id = ?"
             )
        ) {
            preparedStatement.setInt(1, passengerId);
            preparedStatement.setInt(2, tripNumber);

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void saveAddress(Address address) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO airport_jdbc.psg_address (country, city) " +
                             "VALUES (?, ?)"
             )
        ) {

            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public Integer getAddressId(Address address) {
        Integer adr_id;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select adr_id from psg_address where country = ? and city = ?"
             )
        ) {

            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                adr_id = rs.getInt("adr_id");
                return adr_id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public Address getAddressById(int adrId) {
        Address address = null;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from psg_address where adr_id = ? "
             )
        ) {

            preparedStatement.setInt(1, adrId);


            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                address = new Address();
                address.setAdrId(adrId);
                address.setCity(rs.getString("city"));
                address.setCountry(rs.getString("country"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return address;
    }
    public boolean updateAddress(Address address, int adr_id){
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE psg_address " +
                             "SET city = ?," +
                             "country = ?" +
                             "where adr_id = ?; "
             )
        ) {

            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getCountry());
            preparedStatement.setInt(3, adr_id);

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
