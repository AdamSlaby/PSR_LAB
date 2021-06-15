package com.model;

import lombok.*;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "PoliceStation")
@Getter
@Setter
@ToString
public class PoliceStation {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "city")
    private String city;
    @Relationship(type = "POLICESTATION_POLICEMEN")
    private Set<Policeman> policemen = new HashSet<>();

    public PoliceStation(String city) {
        this.city = city;
    }

    public void addPoliceman(Policeman policeman) {
        policemen.add(policeman);
    }
}
