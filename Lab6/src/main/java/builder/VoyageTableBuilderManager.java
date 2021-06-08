package builder;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import manager.Manager;

public class VoyageTableBuilderManager extends Manager {
    public VoyageTableBuilderManager(CqlSession session) {
        super(session);
    }

    public void createTable() {
        CreateTable createTable = SchemaBuilder.createTable("voyage")
                .withPartitionKey("id", DataTypes.INT)
                .withColumn("destination", DataTypes.TEXT)
                .withColumn("price", DataTypes.INT)
                .withColumn("days", DataTypes.INT);
        session.execute(createTable.build());
    }
}
