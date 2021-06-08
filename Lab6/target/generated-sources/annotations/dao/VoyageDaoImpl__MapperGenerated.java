package dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.BoundStatementBuilder;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;
import com.datastax.oss.driver.internal.core.util.concurrent.BlockingOperation;
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures;
import com.datastax.oss.driver.internal.mapper.DaoBase;
import com.datastax.oss.driver.internal.querybuilder.update.DefaultUpdate;
import java.lang.Boolean;
import java.lang.Override;
import java.lang.Throwable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import model.Voyage;
import model.VoyageHelper__MapperGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generated by the DataStax driver mapper, do not edit directly.
 */
public class VoyageDaoImpl__MapperGenerated extends DaoBase implements VoyageDao {
  private static final Logger LOG = LoggerFactory.getLogger(VoyageDaoImpl__MapperGenerated.class);

  private final VoyageHelper__MapperGenerated voyageHelper;

  private final PreparedStatement findByPriceStatement;

  private final PreparedStatement findByIdStatement;

  private final PreparedStatement findAllStatement;

  private final PreparedStatement saveStatement;

  private final PreparedStatement updateStatement;

  private final PreparedStatement deleteStatement;

  private VoyageDaoImpl__MapperGenerated(MapperContext context,
      VoyageHelper__MapperGenerated voyageHelper, PreparedStatement findByPriceStatement,
      PreparedStatement findByIdStatement, PreparedStatement findAllStatement,
      PreparedStatement saveStatement, PreparedStatement updateStatement,
      PreparedStatement deleteStatement) {
    super(context);
    this.voyageHelper = voyageHelper;
    this.findByPriceStatement = findByPriceStatement;
    this.findByIdStatement = findByIdStatement;
    this.findAllStatement = findAllStatement;
    this.saveStatement = saveStatement;
    this.updateStatement = updateStatement;
    this.deleteStatement = deleteStatement;
  }

  @Override
  public PagingIterable<Voyage> findByPrice(int givenPrice) {
    BoundStatementBuilder boundStatementBuilder = findByPriceStatement.boundStatementBuilder();

    boundStatementBuilder = boundStatementBuilder.setInt("givenPrice", givenPrice);

    BoundStatement boundStatement = boundStatementBuilder.build();
    return executeAndMapToEntityIterable(boundStatement, voyageHelper);
  }

  @Override
  public Voyage findById(int id) {
    BoundStatementBuilder boundStatementBuilder = findByIdStatement.boundStatementBuilder();

    boundStatementBuilder = boundStatementBuilder.setInt("id", id);

    BoundStatement boundStatement = boundStatementBuilder.build();
    return executeAndMapToSingleEntity(boundStatement, voyageHelper);
  }

  @Override
  public PagingIterable<Voyage> findAll() {
    BoundStatementBuilder boundStatementBuilder = findAllStatement.boundStatementBuilder();

    BoundStatement boundStatement = boundStatementBuilder.build();
    return executeAndMapToEntityIterable(boundStatement, voyageHelper);
  }

  @Override
  public void save(Voyage data) {
    BoundStatementBuilder boundStatementBuilder = saveStatement.boundStatementBuilder();
    voyageHelper.set(data, boundStatementBuilder, NullSavingStrategy.DO_NOT_SET);

    BoundStatement boundStatement = boundStatementBuilder.build();
    execute(boundStatement);
  }

  @Override
  public void update(Voyage data) {
    BoundStatementBuilder boundStatementBuilder = updateStatement.boundStatementBuilder();
    voyageHelper.set(data, boundStatementBuilder, NullSavingStrategy.DO_NOT_SET);

    BoundStatement boundStatement = boundStatementBuilder.build();
    execute(boundStatement);
  }

  @Override
  public void delete(Voyage data) {
    BoundStatementBuilder boundStatementBuilder = deleteStatement.boundStatementBuilder();

    boundStatementBuilder = boundStatementBuilder.setInt("id", data.getId());

    BoundStatement boundStatement = boundStatementBuilder.build();
    execute(boundStatement);
  }

  public static CompletableFuture<VoyageDao> initAsync(MapperContext context) {
    LOG.debug("[{}] Initializing new instance for keyspace = {} and table = {}",
        context.getSession().getName(),
        context.getKeyspaceId(),
        context.getTableId());
    throwIfProtocolVersionV3(context);
    try {
      // Initialize all entity helpers
      VoyageHelper__MapperGenerated voyageHelper = new VoyageHelper__MapperGenerated(context);
      if ((Boolean)context.getCustomState().get("datastax.mapper.schemaValidationEnabled")) {
        voyageHelper.validateEntityFields();
      }
      List<CompletionStage<PreparedStatement>> prepareStages = new ArrayList<>();
      // Prepare the statement for `findByPrice(int)`:
      SimpleStatement findByPriceStatement_simple = voyageHelper.selectStart().whereRaw("price > :givenPrice ALLOW FILTERING").build();
      LOG.debug("[{}] Preparing query `{}` for method findByPrice(int)",
          context.getSession().getName(),
          findByPriceStatement_simple.getQuery());
      CompletionStage<PreparedStatement> findByPriceStatement = prepare(findByPriceStatement_simple, context);
      prepareStages.add(findByPriceStatement);
      // Prepare the statement for `findById(int)`:
      SimpleStatement findByIdStatement_simple = voyageHelper.selectByPrimaryKeyParts(1).build();
      LOG.debug("[{}] Preparing query `{}` for method findById(int)",
          context.getSession().getName(),
          findByIdStatement_simple.getQuery());
      CompletionStage<PreparedStatement> findByIdStatement = prepare(findByIdStatement_simple, context);
      prepareStages.add(findByIdStatement);
      // Prepare the statement for `findAll()`:
      SimpleStatement findAllStatement_simple = voyageHelper.selectByPrimaryKeyParts(0).build();
      LOG.debug("[{}] Preparing query `{}` for method findAll()",
          context.getSession().getName(),
          findAllStatement_simple.getQuery());
      CompletionStage<PreparedStatement> findAllStatement = prepare(findAllStatement_simple, context);
      prepareStages.add(findAllStatement);
      // Prepare the statement for `save(T)`:
      SimpleStatement saveStatement_simple = voyageHelper.insert().build();
      LOG.debug("[{}] Preparing query `{}` for method save(T)",
          context.getSession().getName(),
          saveStatement_simple.getQuery());
      CompletionStage<PreparedStatement> saveStatement = prepare(saveStatement_simple, context);
      prepareStages.add(saveStatement);
      // Prepare the statement for `update(T)`:
      SimpleStatement updateStatement_simple = SimpleStatement.newInstance(((DefaultUpdate)voyageHelper.updateByPrimaryKey()).asCql());
      LOG.debug("[{}] Preparing query `{}` for method update(T)",
          context.getSession().getName(),
          updateStatement_simple.getQuery());
      CompletionStage<PreparedStatement> updateStatement = prepare(updateStatement_simple, context);
      prepareStages.add(updateStatement);
      // Prepare the statement for `delete(T)`:
      SimpleStatement deleteStatement_simple = voyageHelper.deleteByPrimaryKeyParts(1).build();
      LOG.debug("[{}] Preparing query `{}` for method delete(T)",
          context.getSession().getName(),
          deleteStatement_simple.getQuery());
      CompletionStage<PreparedStatement> deleteStatement = prepare(deleteStatement_simple, context);
      prepareStages.add(deleteStatement);
      // Initialize all method invokers
      // Build the DAO when all statements are prepared
      return CompletableFutures.allSuccessful(prepareStages)
          .thenApply(v -> (VoyageDao) new VoyageDaoImpl__MapperGenerated(context,
              voyageHelper,
              CompletableFutures.getCompleted(findByPriceStatement),
              CompletableFutures.getCompleted(findByIdStatement),
              CompletableFutures.getCompleted(findAllStatement),
              CompletableFutures.getCompleted(saveStatement),
              CompletableFutures.getCompleted(updateStatement),
              CompletableFutures.getCompleted(deleteStatement)))
          .toCompletableFuture();
    } catch (Throwable t) {
      return CompletableFutures.failedFuture(t);
    }
  }

  public static VoyageDao init(MapperContext context) {
    BlockingOperation.checkNotDriverThread();
    return CompletableFutures.getUninterruptibly(initAsync(context));
  }
}