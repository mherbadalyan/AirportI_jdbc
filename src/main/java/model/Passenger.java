package model;

import java.util.Objects;
public class Passenger {
    private int passId;
    private Address address;
    private String name;
    private String phone;

    public Passenger() {
    }
    public Passenger(String name, String phone, Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPassId() {
        return passId;
    }
    public void setPassId(int passId) {
        this.passId = passId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return passId == passenger.passId && Objects.equals(address, passenger.address) && Objects.equals(name, passenger.name) && Objects.equals(phone, passenger.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passId, address, name, phone);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                " passId = " + passId +
                ", name = " + name +
                ", phone='" + phone  +
                ", address = " + address +
                '}';
    }
}