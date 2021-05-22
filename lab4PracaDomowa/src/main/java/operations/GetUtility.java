package operations;

import model.Employee;
import model.Unit;
import oracle.kv.KVStore;
import oracle.kv.Key;
import oracle.kv.ValueVersion;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GetUtility {
    public static void getAllDate(KVStore store) {
        getAllPerTable(store, "employee");
        System.out.println();
        getAllPerTable(store, "vehicle");
        System.out.println();
        getAllUnites(store);
    }

    public static List<Employee> getAllEmployees(KVStore store) {
        List<Employee> employees = new ArrayList<>();
        for (int id = 1; id <= 5; id++) {
            Key key = Key.createKey("employee:" + id + ":" + id);
            Object data = getData(store, key);
            if (data != null)
                employees.add((Employee) data);
        }
        return employees;
    }

    private static void getAllPerTable(KVStore store, String tableName) {
        for (int id = 1; id <= 5; id++) {
            Key key = Key.createKey(tableName + ":" + id + ":" + id);
            Object data = getData(store, key);
            if (data != null)
                System.out.println(key + " => " + data);
        }
    }

    private static void getAllUnites(KVStore store) {
        for (int id = 1; id <= 5; id++) {
            Key key = Key.createKey("unit:" + id);
            Object data = getData(store, key);
            if (data != null)
                System.out.println(key + " => " + data);
        }
    }

    public static void getAllEmployeesByUnit(KVStore store, int unitId) {
        for (int id = 1; id <= 5; id++) {
            Key key = Key.createKey("employee:" + unitId + ":" + id);
            Object data = getData(store, key);
            if (data != null)
                System.out.println(key + " => " + data);
        }
    }

    public static Unit getUnit(KVStore store, int unitId) {
        Key key = Key.createKey("unit:" + unitId);
        Unit data = (Unit) getData(store, key);
        if (data != null)
            return data;
        return null;
    }

    public static void getEmployeesWithSalary(KVStore store, BigDecimal salary) {
        for (int id = 1; id <= 5; id++) {
            Key key = Key.createKey("employee:" + id + ":" + id);
            Employee data = (Employee) getData(store, key);
            if (data != null && salary.compareTo(data.getSalary()) < 0)
                System.out.println(key + " => " + data);
        }
    }

    private static Object getData(KVStore store, Key key) {
        ValueVersion valueVersion = store.get(key);
        if (valueVersion != null) {
            byte[] value = valueVersion.getValue().getValue();
            ByteArrayInputStream bis = new ByteArrayInputStream(value);
            try {
                ObjectInput in = new ObjectInputStream(bis);
                return in.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
}
