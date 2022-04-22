package model;

import java.sql.Date;
import java.util.Objects;

public class Company {
    private String name;
    private Date foundDate;
    public Company() {
    }
    public Company(String name, Date foundDate) {
        this.name = name;
        this.foundDate = foundDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(foundDate, company.foundDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, foundDate);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", foundDate='" + foundDate + '\'' +
                '}';
    }
}
