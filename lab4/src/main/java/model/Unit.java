package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Unit implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String city;
    private String address;

    @Override
    public String toString() {
        return "Unit in " + city + " in address " + address;
    }
}
