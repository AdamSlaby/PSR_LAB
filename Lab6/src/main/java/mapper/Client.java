package mapper;

import builder.CustomersTableBuilderManager;
import builder.TravelAgenciesTableBuilderManager;
import builder.VoyageTableBuilderManager;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import dao.CustomerDao;
import dao.TravelAgencyDao;
import dao.VoyageDao;
import manager.CodecManager;
import manager.KeyspaceManager;
import model.Customer;
import model.TravelAgency;
import model.Voyage;
import utility.PutUtility;

import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder().build()) {
            KeyspaceManager keyspaceManager = new KeyspaceManager(session, "world");
            keyspaceManager.dropKeyspace();
            keyspaceManager.selectKeyspaces();
            keyspaceManager.createKeyspace();
            keyspaceManager.useKeyspace();
            createTables(session);

            CodecManager codecManager = new CodecManager(session);
            codecManager.registerAddressCodec();

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
                        updateCustomer(session);
                        break;
                    }
                    case 3: {
                        deleteTravelAgency(session);
                        break;
                    }
                    case 4: {
                        get(session);
                        break;
                    }
                    case 5: {
                        countVoyageAveragePrice(session);
                        break;
                    }
                    case 6:
                        break;
                    default:
                        System.out.println("Incorrect input. Try again");
                }
            }
        }
    }

    private static void countVoyageAveragePrice(CqlSession session) {
        VoyageMapper voyageMapper = new VoyageMapperBuilder(session).build();
        VoyageDao voyageDao = voyageMapper.voyageDao(CqlIdentifier.fromCql("world"));
        PagingIterable<Voyage> voyages = voyageDao.findAll();
        int sumPrice = 0;
        int voyageCounter = 0;
        for (Voyage voyage : voyages) {
            sumPrice += voyage.getPrice();
            voyageCounter++;
        }
        System.out.println("Average price for voyage is: " + ((double) sumPrice / (double) voyageCounter));
    }

    private static void deleteTravelAgency(CqlSession session) {
        TravelAgencyMapper travelAgencyMapper = new TravelAgencyMapperBuilder(session).build();
        TravelAgencyDao travelAgencyDao = travelAgencyMapper.travelAgencyDao(CqlIdentifier.fromCql("world"));
        System.out.println("Write id of travel agency");
        int travelAgencyId = scanner.nextInt();
        TravelAgency travelAgency = travelAgencyDao.findById(travelAgencyId);
        if (travelAgency != null)
            travelAgencyDao.delete(travelAgency);
    }

    private static void updateCustomer(CqlSession session) {
        CustomerMapper customerMapper = new CustomerMapperBuilder(session).build();
        CustomerDao dao = customerMapper.customerDao(CqlIdentifier.fromCql("world"));
        System.out.println("Write customer id");
        int customerId = scanner.nextInt();
        Customer customer = dao.findById(customerId);
        if (customer != null) {
            System.out.println("Write new name of customer");
            scanner.nextLine();
            customer.setName(scanner.nextLine());
            System.out.println("Write surname of customer");
            customer.setSurname(scanner.nextLine());
            System.out.println("Write age of customer");
            customer.setAge(scanner.nextInt());
            dao.update(customer);
        } else
            System.out.println("Customer with given id does not exist");
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

    private static void createTables(CqlSession session) {
        CustomersTableBuilderManager tableManager = new CustomersTableBuilderManager(session);
        tableManager.createTable();
        VoyageTableBuilderManager builderManager = new VoyageTableBuilderManager(session);
        builderManager.createTable();
        TravelAgenciesTableBuilderManager manager = new TravelAgenciesTableBuilderManager(session);
        manager.createTable();
    }

    private static void get(CqlSession session) {
        getMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                getAllData(session);
                break;
            }
            case 2: {
                getVoyageById(session);
                break;
            }
            case 3: {
                getVoyageByPrice(session);
                break;
            }
            default:
                System.out.println("Incorrect value");
        }
    }

    private static void getVoyageByPrice(CqlSession session) {
        System.out.println("Write minimal price for voyage");
        int price = scanner.nextInt();
        VoyageMapper voyageMapper = new VoyageMapperBuilder(session).build();
        VoyageDao voyageDao = voyageMapper.voyageDao(CqlIdentifier.fromCql("world"));
        PagingIterable<Voyage> voyages = voyageDao.findByPrice(price);
        voyages.forEach(System.out::println);
    }

    private static void getVoyageById(CqlSession session) {
        System.out.println("Write id of voyage");
        int voyageId = scanner.nextInt();
        VoyageMapper voyageMapper = new VoyageMapperBuilder(session).build();
        VoyageDao voyageDao = voyageMapper.voyageDao(CqlIdentifier.fromCql("world"));
        System.out.println(voyageDao.findById(voyageId));
    }

    private static void getAllData(CqlSession session) {
        CustomerMapper customerMapper = new CustomerMapperBuilder(session).build();
        CustomerDao dao = customerMapper.customerDao(CqlIdentifier.fromCql("world"));
        PagingIterable<Customer> customers = dao.findAll();
        customers.forEach(System.out::println);
        System.out.println("");

        VoyageMapper voyageMapper = new VoyageMapperBuilder(session).build();
        VoyageDao voyageDao = voyageMapper.voyageDao(CqlIdentifier.fromCql("world"));
        PagingIterable<Voyage> voyages = voyageDao.findAll();
        voyages.forEach(System.out::println);
        System.out.println("");

        TravelAgencyMapper travelAgencyMapper = new TravelAgencyMapperBuilder(session).build();
        TravelAgencyDao travelAgencyDao = travelAgencyMapper.travelAgencyDao(CqlIdentifier.fromCql("world"));
        PagingIterable<TravelAgency> travelAgencies = travelAgencyDao.findAll();
        travelAgencies.forEach(System.out::println);
    }

    private static void put(CqlSession session) {
        PutUtility.putCustomers(session);
        PutUtility.putVoyage(session);
        PutUtility.putTravelAgencies(session);
    }


}
