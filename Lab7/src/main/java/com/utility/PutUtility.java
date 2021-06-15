package com.utility;

import com.model.*;
import org.neo4j.ogm.session.Session;

import java.util.ArrayList;
import java.util.List;

public class PutUtility {
    private static List<Address> addresses = new ArrayList<>();
    private static List<Policeman> policemen = new ArrayList<>();

    public static void addAddress(Session session) {
        Address address = new Address().builder()
                .street("Mielczarskiego")
                .houseNumber(102)
                .flatNumber(90)
                .build();
        Address address2 = new Address().builder()
                .street("Jagiellonska")
                .houseNumber(55)
                .flatNumber(102)
                .build();
        Address address3 = new Address().builder()
                .street("Krakowska")
                .houseNumber(80)
                .flatNumber(50)
                .build();
        addresses.add(address);
        addresses.add(address2);
        addresses.add(address3);
        AddressService addressService = new AddressService(session);
        addressService.createOrUpdate(address);
        addressService.createOrUpdate(address2);
        addressService.createOrUpdate(address3);
    }

    public static void addPolicemen(Session session) {
        Policeman policeman = new Policeman("Andrzej", "Kozakiewicz", 32, "Posterunkowy");
        policeman.addAddress(addresses.get(0));
        Policeman policeman2 = new Policeman("Maciej", "Młynarczyk", 45, "Podkomisarz");
        policeman2.addAddress(addresses.get(1));
        Policeman policeman3 = new Policeman("Marek", "Majewski", 55, "Komisarz");
        policeman3.addAddress(addresses.get(2));
        policemen.add(policeman);
        policemen.add(policeman2);
        policemen.add(policeman3);

        PolicemanService policemanService = new PolicemanService(session);

        policemanService.createOrUpdate(policeman);
        policemanService.createOrUpdate(policeman2);
        policemanService.createOrUpdate(policeman3);
    }

    public static void addPoliceStation(Session session) {
        PoliceStation policeStation = new PoliceStation("Kielce");
        policeStation.addPoliceman(policemen.get(0));
        PoliceStation policeStation2 = new PoliceStation("Warszawa");
        policeStation2.addPoliceman(policemen.get(1));
        PoliceStation policeStation3 = new PoliceStation("Gdańsk");
        policeStation3.addPoliceman(policemen.get(2));

        PoliceStationService policeStationService = new PoliceStationService(session);

        policeStationService.createOrUpdate(policeStation);
        policeStationService.createOrUpdate(policeStation2);
        policeStationService.createOrUpdate(policeStation3);
    }
}
