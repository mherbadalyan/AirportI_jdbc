package service.fileReader;

import dao.impl.PassengerDaoImpl;
import model.PassInTrip;

import java.io.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class PassInTripFIleReader {
    public static void readFileTrip() {
        PassengerDaoImpl pitDao = new PassengerDaoImpl();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream(
                        "C:\\Users\\user\\Desktop\\homework_JDBC\\homework_JDBC\\pass_in_trip.txt")))) {

            String[] strArr;
            String str;
            while ((str = br.readLine()) != null) {
                strArr = str.split(",");
                PassInTrip passInTrip = new PassInTrip(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]), strArr[3], Date.valueOf((LocalDateTime.parse(strArr[2], formatter)).toLocalDate()));
                pitDao.registerTrip(passInTrip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
