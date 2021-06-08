package mapper;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import dao.VoyageDao;

@Mapper
public interface VoyageMapper {
    @DaoFactory
    VoyageDao voyageDao(@DaoKeyspace CqlIdentifier keyspace);
}
