import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class PutUtility {
    public static void putBooks(MongoCollection<Document> books) {
        Document book1 = new Document("_id", 1)
                .append("title", "W pustyni i w puszczy")
                .append("author", "Henryk Sienkiewicz")
                .append("year", 2012)
                .append("publishing house", "subnautica");
        Document book2 = new Document("_id", 2)
                .append("title", "Krzyżacy")
                .append("author", "Henryk Sienkiewicz")
                .append("year", 2011)
                .append("publishing house", "Nowy swiat");
        Document book3 = new Document("_id", 3)
                .append("title", "Ostatni dzień lipca")
                .append("author", "Bartłomiej Rychter")
                .append("year", 2014)
                .append("publishing house", "Nowa");
        Document book4 = new Document("_id", 4)
                .append("title", "Wiedźmin")
                .append("author", "Andrzej Sapkowski")
                .append("year", 2008)
                .append("publishing house", "Nowy świat");
        Document book5 = new Document("_id", 5)
                .append("title", "Kamienie na szaniec")
                .append("author", "Aleksander Kamiński")
                .append("year", 1955)
                .append("publishing house", "Nowa");
        try {
            books.insertOne(book1);
            books.insertOne(book2);
            books.insertOne(book3);
            books.insertOne(book4);
            books.insertOne(book5);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void putClients(MongoCollection<Document> clients) {
        Document client1 = new Document("_id", 1)
                .append("imie", "Adam")
                .append("nazwisko", "Kowalski")
                .append("age", 20)
                .append("library", 1)
                .append("wypożyczone książki", new Document("book_id", 1));
        Document client2 = new Document("_id", 2)
                .append("imie", "Marek")
                .append("nazwisko", "Jakubowski")
                .append("age", 25)
                .append("library", 1)
                .append("wypożyczone książki", new Document("book_id", 3));
        Document client3 = new Document("_id", 3)
                .append("imie", "Szymon")
                .append("nazwisko", "Woźniak")
                .append("age", 40)
                .append("library", 1)
                .append("wypożyczone książki", new Document("book_id", 4));
        Document client4 = new Document("_id", 4)
                .append("imie", "Jakub")
                .append("nazwisko", "Mikołajczak")
                .append("age", 50)
                .append("library", 1)
                .append("wypożyczone książki", new Document("book_id", 5));
        Document client5 = new Document("_id", 5)
                .append("imie", "Mirosław")
                .append("nazwisko", "Klose")
                .append("age", 60)
                .append("library", 1)
                .append("wypożyczone książki", new Document("book_id", 2));
        try {
            clients.insertOne(client1);
            clients.insertOne(client2);
            clients.insertOne(client3);
            clients.insertOne(client4);
            clients.insertOne(client5);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void putLibraries(MongoCollection<Document> libraries) {
        Document library1 = new Document("_id", 1)
                .append("name", "Biblioteka wojewodzka")
                .append("address", "ul Jagiellońska 102/3")
                .append("city", "Kielce");
        Document library2 = new Document("_id", 2)
                .append("name", "Biblioteka miejska")
                .append("address", "ul Grunwaldzka 33/44")
                .append("city", "Warszawa");
        Document library3 = new Document("_id", 3)
                .append("name", "Biblioteka dla dzieci")
                .append("address", "ul Mielczarskiego 44/22")
                .append("city", "Gdańsk");
        try {
            libraries.insertOne(library1);
            libraries.insertOne(library2);
            libraries.insertOne(library3);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
