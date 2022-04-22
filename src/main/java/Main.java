import service.fileReader.CompanyFileService;
import service.fileReader.PassInTripFIleReader;
import service.fileReader.PassengerFileService;
import service.fileReader.TripFileService;

public class Main {
    public static void main(String[] args) {
        // Reading data from file and putting it to tables
        CompanyFileService.readFIleCompany();
        PassengerFileService.readFilePassenger();
        TripFileService.readFileTrip();
        PassInTripFIleReader.readFileTrip();

    }
}
