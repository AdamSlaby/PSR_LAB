package dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import model.Voyage;

@Dao
public interface VoyageDao extends DaoA<Voyage> {
    @Select(customWhereClause = "price > :givenPrice ALLOW FILTERING")
    PagingIterable<Voyage> findByPrice(int givenPrice);
}
