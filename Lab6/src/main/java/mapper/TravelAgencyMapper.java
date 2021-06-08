package mapper;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import dao.TravelAgencyDao;

@Mapper
public interface TravelAgencyMapper {
    @DaoFactory
    TravelAgencyDao travelAgencyDao(@DaoKeyspace CqlIdentifier keyspace);
}
