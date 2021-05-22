package operations;

import model.Employee;
import model.Unit;
import model.Vehicle;
import oracle.kv.KVStore;
import oracle.kv.Key;
import oracle.kv.Value;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class PutUtility {

    public static void putEmployee(KVStore store, Employee data) {
        Key key = Key.createKey("employee:" + data.getUnitId() + ":" + data.getId());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            oos.flush();
            byte [] bytes = bos.toByteArray();
            Value value = Value.createValue(bytes);
            store.put(key, value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void putVehicle(KVStore store, Vehicle data) {
        Key key = Key.createKey("vehicle:" + data.getUnitId() + ":" + data.getId());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            oos.flush();
            byte [] bytes = bos.toByteArray();
            Value value = Value.createValue(bytes);
            store.put(key, value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void putUnit(KVStore store, Unit data) {
        Key key = Key.createKey("unit:" + data.getId());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            oos.flush();
            byte [] bytes = bos.toByteArray();
            Value value = Value.createValue(bytes);
            store.put(key, value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
