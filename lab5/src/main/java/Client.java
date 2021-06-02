import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Accumulators.avg;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        MongoClient mongoClient = Config.getMongoClient();
        MongoDatabase db = mongoClient.getDatabase(Config.getDatabase());

        int choice = 0;
        while (choice != 6) {
            menu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    put(db);
                    break;
                }
                case 2: {
                    updateClient(db);
                    break;
                }
                case 3: {
                    deleteLibrary(db);
                    break;
                }
                case 4: {
                    get(db);
                    break;
                }
                case 5: {
                    countClientsAverageAge(db);
                    break;
                }
                case 6:
                    break;
                default:
                    System.out.println("Incorrect input. Try again");
            }
        }
    }

    private static void menu() {
        System.out.println("1. Save operation");
        System.out.println("2. Update operation");
        System.out.println("3. Delete operation");
        System.out.println("4. Get operation");
        System.out.println("5. Process operation");
        System.out.println("6. Quit");
    }

    private static void getMenu() {
        System.out.println("1. Get all stored data");
        System.out.println("2. Get book by id");
        System.out.println("3. Get book by year");
    }

    private static void get(MongoDatabase db) {
        getMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                getAllData(db);
                break;
            }
            case 2: {
                getBookById(db);
                break;
            }
            case 3: {
                getBookByYear(db);
                break;
            }
            default:
                System.out.println("Incorrect value");
        }
    }

    private static void getBookByYear(MongoDatabase db) {
        System.out.println("Write book release year");
        int year = scanner.nextInt();
        MongoCollection<Document> books = db.getCollection("books");
        FindIterable<Document> booksWithYear = books.find(gt("year", year));
        for (Document doc:booksWithYear) {
            System.out.println(doc.toString());
        }
    }


    private static void getBookById(MongoDatabase db) {
        System.out.println("Write book id");
        int bookId = scanner.nextInt();
        MongoCollection<Document> books = db.getCollection("books");
        Document book = books.find(eq("_id", bookId)).first();
        System.out.println("Result are books with higher release year than given");
        if (book != null)
            System.out.println(book.toString());
        else
            System.out.println("The book with given id does not exist");
    }

    private static void getAllData(MongoDatabase db) {
        MongoCollection<Document> books = db.getCollection("books");
        for (Document doc : books.find())
            System.out.println(doc.toString());
        System.out.println("");
        MongoCollection<Document> clients = db.getCollection("clients");
        for (Document doc : clients.find())
            System.out.println(doc.toString());
        System.out.println("");
        MongoCollection<Document> libraries = db.getCollection("libraries");
        for (Document doc : libraries.find())
            System.out.println(doc.toString());
    }

    private static void put(MongoDatabase db) {
        MongoCollection<Document> books = db.getCollection("books");
        PutUtility.putBooks(books);
        MongoCollection<Document> clients = db.getCollection("clients");
        PutUtility.putClients(clients);
        MongoCollection<Document> libraries = db.getCollection("libraries");
        PutUtility.putLibraries(libraries);
    }

    private static void updateClient(MongoDatabase db) {
        System.out.println("Write book id to update");
        int bookId = scanner.nextInt();
        System.out.println("Write new title of the book");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Write new author of the book");
        String author = scanner.nextLine();
        System.out.println("Write new release year of the book");
        int year = scanner.nextInt();
        System.out.println("Write new publishing house of the book");
        scanner.nextLine();
        String publishingHouse = scanner.nextLine();
        MongoCollection<Document> clients = db.getCollection("books");
        clients.findOneAndReplace(eq("_id", bookId), new Document("_id", bookId)
                .append("title", title)
                .append("author", author)
                .append("year", year)
                .append("publishing house", publishingHouse));
    }

    private static void deleteLibrary(MongoDatabase db) {
        System.out.println("Write library id to delete");
        int libraryId = scanner.nextInt();
        MongoCollection<Document> libraries = db.getCollection("libraries");
        libraries.deleteOne(eq("_id", libraryId));
    }

    private static void countClientsAverageAge(MongoDatabase db) {
        MongoCollection<Document> clients = db.getCollection("clients");
        Bson group = group("$library", avg("avgAge", "$age"));
        Bson limit = limit(2);
        ArrayList<Document> avgAge = clients.aggregate(Arrays.asList(group, limit)).into(new ArrayList<>());
        System.out.println("Average age for client per library is: ");
        avgAge.forEach(client -> System.out.println(client.toString()));
    }
}
