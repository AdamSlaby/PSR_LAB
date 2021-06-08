// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.cosmos.sample.client;


import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import com.azure.cosmos.sample.common.AccountSettings;
import com.azure.cosmos.sample.model.Customer;
import com.azure.cosmos.sample.model.Response;
import com.azure.cosmos.sample.model.TravelAgency;
import com.azure.cosmos.sample.model.Voyage;
import com.azure.cosmos.sample.utility.PutUtility;
import com.azure.cosmos.util.CosmosPagedIterable;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);

    private CosmosClient client;
    private CosmosDatabase database;
    private static CosmosContainer customers;
    private static CosmosContainer voyages;
    private static CosmosContainer travelAgencies;

    public void close() {
        client.close();
    }

    public static void main(String[] args) {
        Client p = new Client();

        try {
            p.getStartedDemo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            p.close();
        }
        System.exit(0);
    }

    private void getStartedDemo() {
        ArrayList<String> preferredRegions = new ArrayList<>();
        preferredRegions.add("West US");

        client = new CosmosClientBuilder()
            .endpoint(AccountSettings.HOST)
            .key(AccountSettings.MASTER_KEY)
            .preferredRegions(preferredRegions)
            .userAgentSuffix("CosmosDBJavaQuickstart")
            .consistencyLevel(ConsistencyLevel.EVENTUAL)
            .buildClient();

        createDatabaseIfNotExists();
        createContainersIfNotExists();
        scaleContainers();

        int choice = 0;
        while (choice != 6) {
            menu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    put();
                    break;
                }
                case 2: {
                    updateCustomer();
                    break;
                }
                case 3: {
                    deleteTravelAgency();
                    break;
                }
                case 4: {
                    get();
                    break;
                }
                case 5: {
                    countVoyageAveragePrice();
                    break;
                }
                case 6:
                    break;
                default:
                    System.out.println("Incorrect input. Try again");
            }
        }
    }

    private static void menu() {
        System.out.println("1. Save operation");
        System.out.println("2. Update operation");
        System.out.println("3. Delete operation");
        System.out.println("4. Get operation");
        System.out.println("5. Process operation");
        System.out.println("6. Quit");
    }

    private static void getMenu() {
        System.out.println("1. Get all stored data");
        System.out.println("2. Get voyage by id");
        System.out.println("3. Get voyage by price");
    }

    private void countVoyageAveragePrice() {
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        queryOptions.setQueryMetricsEnabled(true);
        try {
            CosmosPagedIterable<Response> responses = voyages.queryItems(
                    "SELECT AVG(Voyage.price) as price FROM Voyage", queryOptions, Response.class);
            System.out.println("Average voyage price is "+ responses.stream().findFirst().get().getPrice());
        }  catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void deleteTravelAgency() {
        System.out.println("Write travel agency id");
        int travelAgencyId = scanner.nextInt();
        CosmosItemResponse<Customer> item = travelAgencies
                .readItem(String.valueOf(travelAgencyId), new PartitionKey(String.valueOf(travelAgencyId)), Customer.class);
        travelAgencies.deleteItem(item.getItem(), new CosmosItemRequestOptions());
    }

    private static void updateCustomer() {
        System.out.println("Write cutsomer id");
        int customerId = scanner.nextInt();
        CosmosItemResponse<Customer> item = customers
                .readItem(String.valueOf(customerId), new PartitionKey(String.valueOf(customerId)), Customer.class);
        if (item != null) {
            System.out.println("Write new name of customer");
            scanner.nextLine();
            item.getItem().setName(scanner.nextLine());
            System.out.println("Write surname of customer");
            item.getItem().setSurname(scanner.nextLine());
            System.out.println("Write age of customer");
            item.getItem().setAge(scanner.nextInt());
            customers.upsertItem(item.getItem());
        } else
            System.out.println("Customer with given id does not exist");
    }

    private static void get() {
        getMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                getAllData();
                break;
            }
            case 2: {
                getVoyageById();
                break;
            }
            case 3: {
                getVoyageByPrice();
                break;
            }
            default:
                System.out.println("Incorrect value");
        }
    }

    private static void getVoyageByPrice() {
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        queryOptions.setQueryMetricsEnabled(true);
        System.out.println("Write minimal price for voyage");
        int price = scanner.nextInt();

        try {
            CosmosPagedIterable<Voyage> items = voyages.queryItems(
                    "SELECT * FROM Voyage WHERE Voyage.price > " + price, queryOptions, Voyage.class);
            items.forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void getVoyageById() {
        System.out.println("Write voyage id");
        int voyageId = scanner.nextInt();
        try {
            CosmosItemResponse<Voyage> item = voyages
                    .readItem(String.valueOf(voyageId), new PartitionKey(String.valueOf(voyageId)), Voyage.class);
            System.out.println(item.getItem());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void put() {
        PutUtility.putCustomers(customers);
        PutUtility.putVoyages(voyages);
        PutUtility.putTravelAgencies(travelAgencies);
    }

    private static void getAllData() {
        try {
            CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
            queryOptions.setQueryMetricsEnabled(true);
            CosmosPagedIterable<Customer> customersList = customers
                    .queryItems("SELECT * FROM Customer", queryOptions, Customer.class);
            customersList.forEach(System.out::println);
            System.out.println();
            CosmosPagedIterable<Voyage> voyages1 = voyages.queryItems("SELECT * FROM Voyage", queryOptions, Voyage.class);
            voyages1.forEach(System.out::println);
            System.out.println();
            CosmosPagedIterable<TravelAgency> travelAgenciesList = travelAgencies
                    .queryItems("SELECT * FROM TravelAgency", queryOptions, TravelAgency.class);
            travelAgenciesList.forEach(System.out::println);

        } catch (CosmosException e) {
            System.err.println(String.format(e.getMessage()));
        }
    }

    private void createDatabaseIfNotExists() {
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists("Database");
        database = client.getDatabase(databaseResponse.getProperties().getId());
    }

    private void createContainersIfNotExists() {
        CosmosContainerProperties containerProperties =
            new CosmosContainerProperties("Customers", "/partitionKey");
        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);
        customers = database.getContainer(containerResponse.getProperties().getId());

        CosmosContainerProperties containerProperties2 =
                new CosmosContainerProperties("Voyages", "/partitionKey");
        CosmosContainerResponse containerResponse2 = database.createContainerIfNotExists(containerProperties2);
        voyages = database.getContainer(containerResponse2.getProperties().getId());

        CosmosContainerProperties containerProperties3 =
                new CosmosContainerProperties("TravelAgencies", "/partitionKey");
        CosmosContainerResponse containerResponse3 = database.createContainerIfNotExists(containerProperties3);
        travelAgencies = database.getContainer(containerResponse3.getProperties().getId());
    }
    
    private void scaleContainers() {
        try {
            ThroughputProperties currentThroughput = customers.readThroughput().getProperties();
            int newThroughput = currentThroughput.getManualThroughput() + 100;
            customers.replaceThroughput(ThroughputProperties.createManualThroughput(newThroughput));

            ThroughputProperties currentThroughput2 = voyages.readThroughput().getProperties();
            int newThroughput2 = currentThroughput2.getManualThroughput() + 100;
            customers.replaceThroughput(ThroughputProperties.createManualThroughput(newThroughput2));

            ThroughputProperties currentThroughput3 = voyages.readThroughput().getProperties();
            int newThroughput3 = currentThroughput3.getManualThroughput() + 100;
            travelAgencies.replaceThroughput(ThroughputProperties.createManualThroughput(newThroughput3));
        } catch (CosmosException e) {
            if (e.getStatusCode() == 400) {
                System.err.println(e.getMessage());
            } else {
                throw e;
            }
        }
    }
}
