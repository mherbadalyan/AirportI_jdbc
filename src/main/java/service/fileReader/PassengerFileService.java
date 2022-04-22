package service.fileReader;

import dao.impl.PassengerDaoImpl;
import model.Address;
import model.Passenger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PassengerFileService {

    public static void readFilePassenger() {
        PassengerDaoImpl passengerDao = new PassengerDaoImpl();
        Integer adr_id;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\user\\Desktop\\homework_JDBC\\homework_JDBC\\passengers.txt")))) {

            String[] strArr;
            String str = br.readLine();

            while ((str = br.readLine()) != null) {
                strArr = str.split(",");
                Address address = new Address(strArr[2], strArr[3]);
                if ((adr_id = passengerDao.getAddressId(address)) == null) {
                    passengerDao.saveAddress(address);
                    adr_id = passengerDao.getAddressId(address);
                }
                address.setAdrId(adr_id);
                passengerDao.save(new Passenger(strArr[0], strArr[1], address));

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
