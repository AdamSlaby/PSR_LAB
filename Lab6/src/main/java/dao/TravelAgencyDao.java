package dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import model.TravelAgency;

@Dao
public interface TravelAgencyDao extends DaoA<TravelAgency> {
}
