package dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import model.Customer;

@Dao
public interface CustomerDao extends DaoA<Customer> {
}
