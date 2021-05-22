package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String surname;
    private BigDecimal salary;
    private long unitId;

    public Employee(String name, String surname, BigDecimal salary, long unitId, int id) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.unitId = unitId;
        this.id = id;
    }

    public long getUnitId() {
        return unitId;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee " + name + " " + surname + " with salary " + salary;
    }
}
