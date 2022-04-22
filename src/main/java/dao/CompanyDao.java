package dao;

import model.*;

import java.util.Set;

public interface CompanyDao {

    Company getById(int id);
    Set<Company> getAll();
    Set<Company> get(int offset, int perPage, String sort);
    boolean save(Company passenger);
    boolean update(Company company, int passId);
    void delete(int companyId);

}
