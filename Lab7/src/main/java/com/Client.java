package com;

import com.model.Address;
import com.model.PoliceStation;
import com.model.Policeman;
import com.model.AddressService;
import com.model.PoliceStationService;
import com.model.PolicemanService;
import com.utility.PutUtility;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Configuration configuration = new Configuration.Builder().uri("bolt://localhost:7687").credentials("neo4j", "password").build();
        SessionFactory sessionFactory = new SessionFactory(configuration, "com");

        Session session = sessionFactory.openSession();

        session.purgeDatabase();

        int choice = 0;
        while (choice != 6) {
            menu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    put(session);
                    break;
                }
                case 2: {
                    updatePoliceman(session);
                    break;
                }
                case 3: {
                    deletePoliceStation(session);
                    break;
                }
                case 4: {
                    get(session);
                    break;
                }
                case 5: {
                    countPolicemanAverageAge(session);
                    break;
                }
                case 6:
                    break;
                default:
                    System.out.println("Incorrect input. Try again");
            }
        }
        sessionFactory.close();
    }

    private static void countPolicemanAverageAge(Session session) {
        PolicemanService policemanService = new PolicemanService(session);
        int counter = 0, ageSum = 0;

        for(Policeman b : policemanService.readAll()) {
            ageSum += b.getAge();
            counter++;
        }
        System.out.println("Average age of policeman is: " + ((float) ageSum / (float) counter));
    }

    private static void updatePoliceman(Session session) {
        System.out.println("Write policeman id");
        long policemanId = scanner.nextLong();
        PolicemanService policemanService = new PolicemanService(session);
        Policeman policeman = policemanService.read(policemanId);
        if (policeman != null) {
            System.out.println("Write new name");
            scanner.nextLine();
            policeman.setName(scanner.nextLine());
            System.out.println("Write new surname");
            policeman.setSurname(scanner.nextLine());
            System.out.println("Write new age");
            policeman.setAge(scanner.nextInt());
            scanner.nextLine();
            System.out.println("Write new rank");
            policeman.setRank(scanner.nextLine());
        } else
            System.out.println("Policeman with given id does not exist");
    }

    private static void deletePoliceStation(Session session) {
        System.out.println("Write police station id");
        long policeStationId = scanner.nextLong();
        PoliceStationService policeStationService = new PoliceStationService(session);
        policeStationService.delete(policeStationId);
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
        System.out.println("2. Get policeman by id");
        System.out.println("3. Get policeman by age");
    }

    private static void put(Session session) {
        PutUtility.addAddress(session);
        PutUtility.addPolicemen(session);
        PutUtility.addPoliceStation(session);
    }

    private static void get(Session session) {
        getMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                getAllData(session);
                break;
            }
            case 2: {
                getPolicemanById(session);
                break;
            }
            case 3: {
                getPolicemanByAge(session);
                break;
            }
            default:
                System.out.println("Incorrect value");
        }
    }

    private static void getPolicemanByAge(Session session) {
        System.out.println("Write minimal age of policeman");
        int age = scanner.nextInt();
        PolicemanService policemanService = new PolicemanService(session);
        System.out.println(policemanService.getPolicemanAboveAge(age));
    }

    private static void getPolicemanById(Session session) {
        System.out.println("Write policeman id");
        long policemanId = scanner.nextLong();
        PolicemanService policemanService = new PolicemanService(session);
        Policeman policeman = policemanService.read(policemanId);
        if (policeman != null)
            System.out.println(policeman);
        else
            System.out.println("Policeman with given id does not exist");
    }

    private static void getAllData(Session session) {
        AddressService addressService = new AddressService(session);
        for(Address b : addressService.readAll())
            System.out.println(b);
        System.out.println();
        PolicemanService policemanService = new PolicemanService(session);
        for(Policeman b : policemanService.readAll())
            System.out.println(b);
        System.out.println();
        PoliceStationService policeStationService = new PoliceStationService(session);
        for(PoliceStation b : policeStationService.readAll())
            System.out.println(b);
    }
}
