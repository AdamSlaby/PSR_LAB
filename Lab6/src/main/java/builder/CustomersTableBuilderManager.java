package builder;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.CreateType;
import com.datastax.oss.driver.api.querybuilder.schema.Drop;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import manager.Manager;

public class CustomersTableBuilderManager extends Manager {
    public CustomersTableBuilderManager(CqlSession session) {
        super(session);
    }

    public void createTable() {
        CreateType createType = SchemaBuilder.createType("address")
                .withField("street", DataTypes.TEXT)
                .withField("houseNumber", DataTypes.INT)
                .withField("flatNumber", DataTypes.INT);

        session.execute(createType.build());

        CreateTable createTable = SchemaBuilder.createTable("customer")
                .withPartitionKey("id", DataTypes.INT)
                .withColumn("name", DataTypes.TEXT)
                .withColumn("surname", DataTypes.TEXT)
                .withColumn("age", DataTypes.INT)
                .withColumn("address", QueryBuilder.udt("address"));
        session.execute(createTable.build());
    }
}
