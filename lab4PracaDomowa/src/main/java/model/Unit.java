package model;

import java.io.Serializable;

public class Unit implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String city;
    private String address;

    public Unit(String city, String address, int id) {
        this.city = city;
        this.address = address;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Unit in " + city + " in address " + address;
    }
}
