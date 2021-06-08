package com.azure.cosmos.sample.utility;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.sample.model.Address;
import com.azure.cosmos.sample.model.Customer;
import com.azure.cosmos.sample.model.TravelAgency;
import com.azure.cosmos.sample.model.Voyage;

public class PutUtility {
    public static void putCustomers(CosmosContainer customers) {
        Address address = new Address().builder()
                .street("Marcinkowska")
                .houseNumber(103)
                .flatNumber(20)
                .postcode("25-222")
                .build();
        Customer customer = new Customer().builder()
                .id("1")
                .partitionKey("1")
                .name("Marek")
                .surname("Maciejewski")
                .age(24)
                .address(address)
                .build();
        Customer customer2 = new Customer().builder()
                .id("2")
                .partitionKey("2")
                .name("Wojtek")
                .surname("Gola")
                .age(30)
                .address(address)
                .build();
        Customer customer3 = new Customer().builder()
                .id("3")
                .partitionKey("3")
                .name("Michał")
                .surname("Morawiecki")
                .age(55)
                .address(address)
                .build();
        try {
            customers.createItem(customer, new PartitionKey(customer.getPartitionKey()), new CosmosItemRequestOptions());
            customers.createItem(customer2, new PartitionKey(customer2.getPartitionKey()), new CosmosItemRequestOptions());
            customers.createItem(customer3, new PartitionKey(customer3.getPartitionKey()), new CosmosItemRequestOptions());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    public static void putVoyages(CosmosContainer voyages) {
        Voyage voyage = new Voyage().builder()
                .id("1")
                .partitionKey("1")
                .destination("Hawaje")
                .price(25000)
                .days(7)
                .build();
        Voyage voyage2 = new Voyage().builder()
                .id("2")
                .partitionKey("2")
                .destination("Karaiby")
                .price(20000)
                .days(14)
                .build();
        Voyage voyage3 = new Voyage().builder()
                .id("3")
                .partitionKey("3")
                .destination("Turcja")
                .price(18000)
                .days(11)
                .build();
        try {
            voyages.createItem(voyage, new PartitionKey(voyage.getPartitionKey()), new CosmosItemRequestOptions());
            voyages.createItem(voyage2, new PartitionKey(voyage2.getPartitionKey()), new CosmosItemRequestOptions());
            voyages.createItem(voyage3, new PartitionKey(voyage3.getPartitionKey()), new CosmosItemRequestOptions());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }


    public static void putTravelAgencies(CosmosContainer travelAgencies) {
        Address address = new Address().builder()
                .street("Mielczarskiego")
                .flatNumber(202)
                .houseNumber(20)
                .postcode("30-222")
                .build();
        TravelAgency travelAgency = new TravelAgency().builder()
                .id("1")
                .partitionKey("1")
                .name("Ikar")
                .address(address)
                .build();
        TravelAgency travelAgency2 = new TravelAgency().builder()
                .id("2")
                .partitionKey("2")
                .name("Lot")
                .address(address)
                .build();
        TravelAgency travelAgency3 = new TravelAgency().builder()
                .id("3")
                .partitionKey("3")
                .name("Kreta")
                .address(address)
                .build();
        try {
            travelAgencies.createItem(travelAgency, new PartitionKey(travelAgency.getPartitionKey()), new CosmosItemRequestOptions());
            travelAgencies.createItem(travelAgency2, new PartitionKey(travelAgency2.getPartitionKey()), new CosmosItemRequestOptions());
            travelAgencies.createItem(travelAgency3, new PartitionKey(travelAgency3.getPartitionKey()), new CosmosItemRequestOptions());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }
}
