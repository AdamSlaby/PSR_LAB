package dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

public interface DaoA<T> {
    @Select
    T findById(int id);

    @Select
    PagingIterable<T> findAll();

    @Insert
    void save(T data);

    @Update
    void update(T data);

    @Delete
    void delete(T data);
}
