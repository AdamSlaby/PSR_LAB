package com.model;

import lombok.*;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Policeman")
@Getter
@Setter
@ToString
public class Policeman {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "name")
    private String name;
    @Property(name = "surname")
    private String surname;
    @Property(name = "age")
    private int age;
    @Property(name = "rank")
    private String rank;
    @Relationship(type = "POLICEMAN_ADDRESSES")
    private Set<Address> addresses = new HashSet<>();;

    public Policeman(String name, String surname, int age, String rank) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.rank = rank;

    }

    public void addAddress(Address address) {
        addresses.add(address);
    }
}
