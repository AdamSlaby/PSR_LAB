package client;

import config.Config;
import model.Employee;
import model.Unit;
import model.Vehicle;
import operations.GetUtility;
import operations.PutUtility;
import oracle.kv.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final static KVStore store = KVStoreFactory.getStore(Config.getConfig());
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        try {
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
                        updateUnit();
                        break;
                    }
                    case 3: {
                        deleteVehicle();
                        break;
                    }
                    case 4: {
                        get();
                        break;
                    }
                    case 5: {
                        countEmployeeAverageSalary();
                        break;
                    }
                    case 6:
                        break;
                    default:
                        System.out.println("Incorrect input. Try again");
                }
            }
        } catch (FaultException e) {
            e.printStackTrace();
            System.out.println("Please make sure a store is running.");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static void countEmployeeAverageSalary() {
        List<Employee> employees = GetUtility.getAllEmployees(store);
        BigDecimal salary =  BigDecimal.valueOf(0);
        int employeeCount = 0;
        for (Employee employee:employees) {
            salary = salary.add(employee.getSalary());
            employeeCount++;
        }
        System.out.println("Average salary per employee = " +
                salary.divideToIntegralValue(BigDecimal.valueOf(employeeCount)));
    }

    private static void deleteVehicle() {
        System.out.println("Write unit id where is that vehicle");
        int unitId = scanner.nextInt();
        System.out.println("Write vehicle id");
        int vehicleId = scanner.nextInt();
        store.delete(Key.createKey("vehicle:" + unitId + ":" + vehicleId));
    }

    private static void updateUnit() {
        System.out.println("Write unit id");
        int unitId = scanner.nextInt();
        scanner.nextLine();
        Unit unit = GetUtility.getUnit(store, unitId);
        if (unit == null)
            System.out.println("Unit doesn't exist");
        else {
            System.out.println("Write new city");
            unit.setCity(scanner.nextLine());
            System.out.println("Write new address");
            unit.setAddress(scanner.nextLine());
            PutUtility.putUnit(store, unit);
        }
    }

    private static void menu() {
        System.out.println("1. Save operation");
        System.out.println("2. Update operation (Update unit)");
        System.out.println("3. Delete operation (Delete vehicle)");
        System.out.println("4. Get operation");
        System.out.println("5. Process operation (Employee average salary)");
        System.out.println("6. Quit");
    }

    private static void getMenu() {
        System.out.println("1. Get all stored data");
        System.out.println("2. Get employee by unit idg");
        System.out.println("3. Get all employees with salary");
    }

    private static void get() {
        getMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                GetUtility.getAllDate(store);
                break;
            }
            case 2: {
                getEmployeeByUnit();
                break;
            }
            case 3: {
                getEmployeeWithSalary();
                break;
            }
            default:
                System.out.println("Incorrect value");
        }
    }

    private static void getEmployeeWithSalary() {
        System.out.println("Write employee salary");
        BigDecimal salary = scanner.nextBigDecimal();
        System.out.println("Program returns employees with salary above given");
        GetUtility.getEmployeesWithSalary(store, salary);
    }

    private static void getEmployeeByUnit() {
        System.out.println("Write unit id");
        int unitId = scanner.nextInt();
        GetUtility.getAllEmployeesByUnit(store, unitId);
    }

    private static void put() {
        Unit unit = new Unit("Kielce", "ul Grunwaldzka 25/33", 1);
        Unit unit2 = new Unit("Warszawa", "ul Mielczarskiego 100/35", 2);
        Unit unit3 = new Unit("Kraków", "ul Zimna 111/23", 3);
        Unit unit4 = new Unit("Wrocław", "ul Mała 22/13", 4);
        Unit unit5 = new Unit("Gdańsk", "ul Szkolna 111/43", 5);
        PutUtility.putUnit(store, unit);
        PutUtility.putUnit(store, unit2);
        PutUtility.putUnit(store, unit3);
        PutUtility.putUnit(store, unit4);
        PutUtility.putUnit(store, unit5);

        Vehicle vehicle = new Vehicle("Mercedes-Benz", 26, 1, 1);
        Vehicle vehicle2 = new Vehicle("Suzuki", 20, 2, 2);
        Vehicle vehicle3 = new Vehicle("Volvo", 26, 3, 3);
        Vehicle vehicle4 = new Vehicle("Volkswagen", 21, 4, 4);
        Vehicle vehicle5 = new Vehicle("Audi", 30, 5, 5);
        PutUtility.putVehicle(store, vehicle);
        PutUtility.putVehicle(store, vehicle2);
        PutUtility.putVehicle(store, vehicle3);
        PutUtility.putVehicle(store, vehicle4);
        PutUtility.putVehicle(store, vehicle5);

        Employee employee = new Employee("Maciek", "Kowalski", BigDecimal.valueOf(1920.80), 1, 1);
        PutUtility.putEmployee(store, employee);
        Employee employee2 = new Employee("Adam", "Markiewicz", BigDecimal.valueOf(2550.80), 2, 2);
        PutUtility.putEmployee(store, employee2);
        Employee employee3 = new Employee("Marek", "Malczyński", BigDecimal.valueOf(3500.80), 3, 3);
        PutUtility.putEmployee(store, employee3);
        Employee employee4 = new Employee("Kuba", "Gola", BigDecimal.valueOf(4000.00), 4, 4);
        PutUtility.putEmployee(store, employee4);
        Employee employee5 = new Employee("Waldek", "Dymek", BigDecimal.valueOf(3200.00), 5, 5);
        PutUtility.putEmployee(store, employee5);
    }
}
