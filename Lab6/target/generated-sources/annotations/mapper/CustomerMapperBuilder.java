package mapper;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperBuilder;
import com.datastax.oss.driver.internal.mapper.DefaultMapperContext;
import java.lang.Override;

/**
 * Builds an instance of {@link CustomerMapper} wrapping a driver {@link CqlSession}.
 *
 * <p>Generated by the DataStax driver mapper, do not edit directly.
 */
public class CustomerMapperBuilder extends MapperBuilder<CustomerMapper> {
  public CustomerMapperBuilder(CqlSession session) {
    super(session);
  }

  @Override
  public CustomerMapper build() {
    DefaultMapperContext context = new DefaultMapperContext(session, defaultKeyspaceId, defaultExecutionProfileName, defaultExecutionProfile, customState);
    return new CustomerMapperImpl__MapperGenerated(context);
  }
}
