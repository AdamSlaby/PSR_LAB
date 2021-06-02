package client;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import config.Config;
import model.Book;
import model.Library;
import model.Reader;
import utility.PutUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AmazonDynamoDB client = Config.getConfig();
        DynamoDB dynamoDB = new DynamoDB(client);

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        createTables(dynamoDB, mapper);

        int choice = 0;
        while (choice != 6) {
            menu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    put(mapper);
                    break;
                }
                case 2: {
                    updateBook(mapper);
                    break;
                }
                case 3: {
                    deleteLibrary(mapper);
                    break;
                }
                case 4: {
                    get(mapper);
                    break;
                }
                case 5: {
                    countReadersAverageAge(mapper);
                    break;
                }
                case 6:
                    break;
                default:
                    System.out.println("Incorrect input. Try again");
            }
        }
    }

    private static void createTables(DynamoDB dynamoDB, DynamoDBMapper mapper) {
        try {
            dynamoDB.getTable("Book").delete();
            dynamoDB.getTable("Reader").delete();
            dynamoDB.getTable("Library").delete();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        CreateTableRequest createTableRequest = mapper.generateCreateTableRequest(Book.class);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(25L, 25L));
        dynamoDB.createTable(createTableRequest);

        CreateTableRequest createTableRequest2 = mapper.generateCreateTableRequest(Reader.class);
        createTableRequest2.setProvisionedThroughput(new ProvisionedThroughput(25L, 25L));
        dynamoDB.createTable(createTableRequest2);

        CreateTableRequest createTableRequest3 = mapper.generateCreateTableRequest(Library.class);
        createTableRequest3.setProvisionedThroughput(new ProvisionedThroughput(25L, 25L));
        dynamoDB.createTable(createTableRequest3);
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

    private static void put(DynamoDBMapper mapper) {
        PutUtility.putBooks(mapper);
        PutUtility.putReaders(mapper);
        PutUtility.putLibraries(mapper);
    }

    private static void deleteLibrary(DynamoDBMapper mapper) {
        System.out.println("Write library id");
        int libraryId = scanner.nextInt();
        Library library = mapper.load(Library.class, libraryId);
        if (library != null)
            mapper.delete(library);
    }

    private static void updateBook(DynamoDBMapper mapper) {
        System.out.println("Write book id");
        int bookId = scanner.nextInt();
        Book book = mapper.load(Book.class, bookId);

        System.out.println("Write new title of book");
        scanner.nextLine();
        book.setTitle(scanner.nextLine());

        System.out.println("Write new author of book");
        book.setAuthor(scanner.nextLine());

        System.out.println("Write new release year of book");
        book.setYear(Integer.parseInt(scanner.nextLine()));

        System.out.println("Write new publishing house of book");
        book.setPublishingHouse(scanner.nextLine());

        mapper.save(book);
    }

    private static void countReadersAverageAge(DynamoDBMapper mapper) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Reader> readers = mapper.scan(Reader.class, scanExpression);
        int ageSum = 0;
        for (Reader reader:readers)
            ageSum += reader.getAge();
        System.out.println("Average age of readers is " + ((float)ageSum / (float) readers.size()));
    }

    private static void get(DynamoDBMapper mapper) {
        getMenu();
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: {
                getAllData(mapper);
                break;
            }
            case 2: {
                getBookById(mapper);
                break;
            }
            case 3: {
                getBookByYear(mapper);
                break;
            }
            default:
                System.out.println("Incorrect value");
        }
    }

    private static void getBookByYear(DynamoDBMapper mapper) {
        System.out.println("Write release year of book");
        int year = scanner.nextInt();
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(String.valueOf(year)));

        DynamoDBScanExpression scanExpression  = new DynamoDBScanExpression()
                .withFilterExpression("releaseYear > :val1").withExpressionAttributeValues(eav);
        List<Book> books = mapper.scan(Book.class, scanExpression);
        if (books != null)
            books.forEach(System.out::println);
        else
            System.out.println("The books with release year above given does not exist");
    }

    private static void getBookById(DynamoDBMapper mapper) {
        System.out.println("Write book id");
        int bookId = scanner.nextInt();
        Book book = mapper.load(Book.class, bookId);
        if (book != null)
            System.out.println(book);
        else
            System.out.println("The book with given id does not exist");
    }

    private static void getAllData(DynamoDBMapper mapper) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Book> books = mapper.scan(Book.class, scanExpression);
        books.forEach(System.out::println);
        System.out.println("");

        List<Reader> readers = mapper.scan(Reader.class, scanExpression);
        readers.forEach(System.out::println);
        System.out.println("");

        List<Library> libraries = mapper.scan(Library.class, scanExpression);
        libraries.forEach(System.out::println);
    }
}
