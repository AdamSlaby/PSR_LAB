package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
public class Employee implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private BigDecimal salary;
    private long unitId;

    @Override
    public String toString()
    {
        return "Employee " + name + " " + surname + " with salary " + salary;
    }
}
