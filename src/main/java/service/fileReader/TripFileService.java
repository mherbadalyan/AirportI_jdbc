package service.fileReader;

import dao.impl.TripDaoImpl;
import model.Trip;

import java.io.*;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
public class TripFileService {
    public static void readFileTrip() {
        TripDaoImpl tripDao = new TripDaoImpl();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream(
                        "C:\\Users\\user\\Desktop\\homework_JDBC\\homework_JDBC\\trip.txt")))) {

            String[] strArr;
            String str;
            while ((str = br.readLine()) != null) {
                strArr = str.split(",");
                Trip trip = new Trip(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]), strArr[2], strArr[3], strArr[4], Time.valueOf(LocalDateTime.parse(strArr[5], formatter).toLocalTime()), Time.valueOf(LocalTime.parse(strArr[6], formatter)));

                tripDao.save(trip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
