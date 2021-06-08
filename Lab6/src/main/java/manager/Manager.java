package manager;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;

import java.time.Duration;

public class Manager {
    final private static Duration TIMEOUT = Duration.ofSeconds(10);

    protected CqlSession session;

    public Manager(CqlSession session) {
        this.session = session;
    }

    public void executeSimpleStatement(String statement) {
        session.execute(new SimpleStatementBuilder(statement).build().setTimeout(TIMEOUT));
        System.out.println("Statement \"" + statement + "\" executed successfully");
    }

    public void executeSimpleStatement(SimpleStatement statement) {
        session.execute(statement.setTimeout(TIMEOUT));
        System.out.println("Statement \"" + statement.getQuery() + "\" executed successfully");
    }
}
