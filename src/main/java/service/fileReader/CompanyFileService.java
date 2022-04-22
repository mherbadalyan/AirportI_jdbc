package service.fileReader;

import dao.impl.CompanyDaoImpl;
import model.Company;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompanyFileService {
    public static void readFIleCompany() {
        CompanyDaoImpl companyDaoImpl = new CompanyDaoImpl();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream
                        ("C:\\Users\\user\\Desktop\\homework_JDBC\\homework_JDBC\\companies.txt")))) {

            String[] strArr;
            String str = br.readLine();
            while ((str = br.readLine()) != null) {
                strArr = str.split(",");
                Company company = new Company(
                        strArr[0],
                        Date.valueOf(LocalDate.parse(strArr[1], formatter)));

                companyDaoImpl.save(company);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
