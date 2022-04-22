package dao.impl;

import dao.CompanyDao;
import model.Company;
import service.DBConnectionService;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompanyDaoImpl implements CompanyDao {
    @Override
    public Company getById(int id) {
        Company company = null;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from company where company_id = ? "
             )
        ) {

            preparedStatement.setInt(1, id);


            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                company = new Company();
                company.setFoundDate(rs.getDate("found_date"));
                company.setName(rs.getString("company_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return company;
    }

    @Override
    public Set<Company> getAll() {
        Set<Company> companySet = new HashSet<>();
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from company"
             )
        ) {
            ResultSet rs = preparedStatement.executeQuery();
            Company company;
            while (rs.next()) {
                company = new Company(
                        rs.getString("company_name"),
                        rs.getDate("found_date")
                );
                companySet.add(company);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return companySet;
    }

    @Override
    public Set<Company> get(int offset, int perPage, String sort) {
        Set<Company> companySet = null;
        Company company;
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from company " +
                             "order by " + sort + " limit ? offset ? ;"
             )
        ) {

            preparedStatement.setInt(1, perPage);
            preparedStatement.setInt(2, offset);


            ResultSet rs = preparedStatement.executeQuery();
            companySet = new LinkedHashSet<>();
            while (rs.next()) {
                company = new Company(
                        rs.getString("company_name"),
                        rs.getDate("found_date")
                );
                companySet.add(company);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return companySet;
    }

    @Override
    public boolean save(Company company) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO company (company_name, found_date) " +
                                     "VALUES (?, ?)"
                     )
        ) {

            preparedStatement.setString(1, company.getName());
            preparedStatement.setDate(2, company.getFoundDate());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Company company, int id) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE company " +
                             "SET company_name = ?," +
                             "found_date = ?" +
                             "where company_id = ?; "
             )
        ) {

            preparedStatement.setString(1, company.getName());
            preparedStatement.setDate(2, company.getFoundDate());
            preparedStatement.setInt(3, id);

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public void delete(int companyId) {
        try (Connection connection = DBConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from company where company_id = ? "
             )
        ) {
            preparedStatement.setInt(1, companyId);

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
