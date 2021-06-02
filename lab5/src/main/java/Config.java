import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Config {
    private static String database = "library";

    public static MongoClient getMongoClient() {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        return new MongoClient(connectionString);
    }

    public static String getDatabase() {
        return database;
    }
}
