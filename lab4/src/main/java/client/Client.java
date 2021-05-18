package client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import java.util.Map;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import config.HConfig;
import model.Employee;
import model.Unit;
import model.Vehicle;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.Future;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws UnknownHostException {
        ClientConfig clientConfig = HConfig.getClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        int choice = 0;
        while (choice != 7) {
            menu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    put(client);
                    break;
                }
                case 2: {
                    updateVehicle(client);
                    break;
                }
                case 3: {
                    deleteVehicle(client);
                    break;
                }
                case 4: {
                    get(client);
                    break;
                }
                case 5: {
                    countEmployeeAverageSalary(client);
                    break;
                }
                case 6: {
                    countEmployeeAverageSalaryOnServer(client);
                    break;
                }
                case 7:
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
        System.out.println("5. Process operation on client side");
        System.out.println("6. Process operation on server side");
        System.out.println("7. Quit");
    }

    private static void getMenu() {
        System.out.println("1. Get all stored data");
        System.out.println("2. Get employee by pesel");
        System.out.println("3. Get all employyes with salary above 2500");
    }

    private static void deleteMenu() {
        System.out.println("1. Delete employee");
        System.out.println("2. Delete vehicle");
        System.out.println("3. Delete unit");
    }

    private static void get(HazelcastInstance client) {
        getMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                getAllData(client);
                break;
            }
            case 2: {
                getEmployeeByPesel(client);
                break;
            }
            case 3: {
                getEmployeeWithSalary(client);
                break;
            }
            default:
                System.out.println("Incorrect value");
        }

    }

    private static void getEmployeeWithSalary(HazelcastInstance client) {
        IMap<String, Employee> employees = client.getMap("employees");
        Predicate<?, ?> predicate = Predicates.greaterThan("salary", 2500);
        Collection<Employee> result = employees.values(Predicates.and(predicate));
        result.forEach(System.out::println);
    }


    private static void getEmployeeByPesel(HazelcastInstance client) {
        System.out.println("Write pesel of employee");
        scanner.nextLine();
        String pesel = scanner.nextLine();
        IMap<String, Employee> employees = client.getMap("employees");
        Employee employee = employees.get(pesel);
        if (employee != null) {
            System.out.println(employee);
        } else
            System.out.println("Nie ma takiego pracownika");

    }

    private static void getAllData(HazelcastInstance client) {
        IMap<String, Vehicle> vehicles = client.getMap("vehicles");
        System.out.println("All vehicles: ");
        for (Entry<String, Vehicle> e : vehicles.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }

        IMap<Long, Unit> units = client.getMap("units");
        System.out.println("All units: ");
        for (Entry<Long, Unit> e : units.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }

        IMap<String, Employee> employees = client.getMap("employees");
        System.out.println("All employees: ");
        for (Entry<String, Employee> e : employees.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
    }

    private static void put(HazelcastInstance client) {
        IMap<Long, Unit> units = client.getMap("units");
        Unit unit = new Unit("Kielce", "ul Grunwaldzka 25/33");
        Unit unit2 = new Unit("Warszawa", "ul Mielczarskiego 100/35");
        Unit unit3 = new Unit("Kraków", "ul Zimna 111/23");
        Unit unit4 = new Unit("Wrocław", "ul Mała 22/13");
        Unit unit5 = new Unit("Gdańsk", "ul Szkolna 111/43");
        units.put(1L, unit);
        units.put(2L, unit2);
        units.put(3L, unit3);
        units.put(4L, unit4);
        units.put(5L, unit5);

        IMap<String, Vehicle> vehicles = client.getMap("vehicles");
        Vehicle vehicle = new Vehicle("Mercedes-Benz", 26, 1);
        Vehicle vehicle2 = new Vehicle("Suzuki", 20, 2);
        Vehicle vehicle3 = new Vehicle("Volvo", 26, 3);
        Vehicle vehicle4 = new Vehicle("Volkswagen", 21, 4);
        Vehicle vehicle5 = new Vehicle("Audi", 30, 5);
        vehicles.put("1GYEC63808R212612", vehicle);
        vehicles.put("WBAFU7C58BC771593", vehicle2);
        vehicles.put("WDDGF54X68F007762", vehicle3);
        vehicles.put("1GKEC16Z62J197556", vehicle4);
        vehicles.put("3N1CB51D21L520536", vehicle5);

        IMap<String, Employee> employees = client.getMap("employees");
        Employee employee = new Employee("Maciek", "Kowalski", BigDecimal.valueOf(1920.80), 1);
        Employee employee2 = new Employee("Adam", "Markiewicz", BigDecimal.valueOf(2550.80), 2);
        Employee employee3 = new Employee("Marek", "Malczyński", BigDecimal.valueOf(3500.80), 2);
        Employee employee4 = new Employee("Kuba", "Gola", BigDecimal.valueOf(4000.00), 4);
        Employee employee5 = new Employee("Waldek", "Dymek", BigDecimal.valueOf(3200.00), 5);
        employees.put("02212549654", employee);
        employees.put("57010567555", employee2);
        employees.put("74092611431", employee3);
        employees.put("63070979135", employee4);
        employees.put("48101173319", employee5);
    }

    private static void updateVehicle(HazelcastInstance client) {
        IMap<String, Vehicle> vehicles = client.getMap("vehicles");
        Vehicle vehicle = vehicles.get("1GYEC63808R212612");
        vehicle.setMark("Suzuki");
        vehicles.put("1GYEC63808R212612", vehicle);
    }

    private static void deleteVehicle(HazelcastInstance client) {
        deleteMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: {
                System.out.println("Write employee pesel");
                String pesel = scanner.nextLine();
                IMap<String, Employee> employees = client.getMap("employees");
                employees.delete(pesel);
                break;
            }
            case 2: {
                System.out.println("Write vin number");
                String vin = scanner.nextLine();
                IMap<String, Vehicle> vehicles = client.getMap("vehicles");
                vehicles.delete(vin);
                break;
            }
            case 3: {
                System.out.println("Write unit id");
                long id = scanner.nextLong();
                scanner.nextLine();
                if (isUnitDeletable(client, id)) {
                    IMap<Long, Unit> units = client.getMap("units");
                    units.delete(id);
                } else
                    System.out.println("Something has reference to this unit. The operation cannot be completed");
                break;
            }
            default:
                System.out.println("Incorrect input");
        }
    }

    private static boolean isUnitDeletable(HazelcastInstance client, long unitId) {
        Map<String, Employee> employees = client.getMap("employees");
        boolean isNotOccurred = true;
        for(Employee employee : employees.values()) {
            if (employee.getUnitId() == unitId)
                isNotOccurred = false;
        }
        Map<String, Vehicle> vehicles = client.getMap("vehicles");
        for(Vehicle vehicle : vehicles.values()) {
            if (vehicle.getUnitId() == unitId)
                isNotOccurred = false;
        }
        return isNotOccurred;
    }

    private static void countEmployeeAverageSalary(HazelcastInstance client) {
        IMap<String, Employee> employees = client.getMap("employees");
        BigDecimal salary =  BigDecimal.valueOf(0);
        int employeeCount = 0;
        for (Entry<String, Employee> e : employees.entrySet()) {
            salary = salary.add(e.getValue().getSalary());
            employeeCount++;
        }
        System.out.println("Employee average salary is " + salary.divideToIntegralValue(BigDecimal.valueOf(employeeCount)));
    }

    private static void countEmployeeAverageSalaryOnServer(HazelcastInstance client) {
        IExecutorService executorService = client.getExecutorService("exec");
        Future<BigDecimal> averageSalary = executorService.submit(new GetAvgSalary());
        try {
            System.out.println("Employee average salary is " + averageSalary.get());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}