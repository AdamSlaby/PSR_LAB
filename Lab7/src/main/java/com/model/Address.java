package com.model;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.util.Objects;

@NodeEntity(label = "Address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "street")
    private String street;
    @Property(name = "houseNumber")
    private int houseNumber;
    @Property(name = "flatNumber")
    private int flatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return houseNumber == address.houseNumber && flatNumber == address.flatNumber && Objects.equals(id, address.id) && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, houseNumber, flatNumber);
    }
}
