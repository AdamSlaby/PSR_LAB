package client;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.map.IMap;
import model.Employee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Callable;

public class GetAvgSalary implements Callable<BigDecimal>, Serializable, HazelcastInstanceAware
{
    private static final long serialVersionUID = 1L;
    private transient HazelcastInstance instance;

    @Override
    public BigDecimal call() throws Exception
    {
        IMap<String, Employee> employees = instance.getMap("employees");
        BigDecimal salary =  BigDecimal.valueOf(0);
        int employeeCount = 0;
        for (Map.Entry<String, Employee> e : employees.entrySet()) {
            salary = salary.add(e.getValue().getSalary());
            employeeCount++;
        }
        return salary.divideToIntegralValue(BigDecimal.valueOf(employeeCount));
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance)
    {
        this.instance = hazelcastInstance;
    }
}
