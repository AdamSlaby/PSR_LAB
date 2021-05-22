package model;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String mark;
    private int seatsCount;
    private long unitId;

    public Vehicle(String mark, int seatsCount, long unitId, int id) {
        this.mark = mark;
        this.seatsCount = seatsCount;
        this.unitId = unitId;
        this.id = id;
    }

    public long getUnitId() {
        return unitId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return "Vehicle with mark: " + mark + " and seats count = " + seatsCount;
    }
}
