package builder;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import manager.Manager;

public class TravelAgenciesTableBuilderManager extends Manager {
    public TravelAgenciesTableBuilderManager(CqlSession session) {
        super(session);
    }

    public void createTable() {
        CreateTable createTable = SchemaBuilder.createTable("travel_agency")
                .withPartitionKey("id", DataTypes.INT)
                .withColumn("name", DataTypes.TEXT)
                .withColumn("address", QueryBuilder.udt("address"));
        session.execute(createTable.build());
    }
}
