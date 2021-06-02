package utility;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import model.Book;
import model.Library;
import model.Reader;

import java.util.ArrayList;

public class PutUtility {
    public static void putBooks(DynamoDBMapper mapper) {
        Book book1 = new Book().builder()
                .id(1)
                .title("W pustyni i w puszczy")
                .author("Henryk Sienkiewicz")
                .year(2012)
                .publishingHouse("subnautica").build();
        Book book2 = new Book().builder()
                .id(2)
                .title("Ostatni dzień lipca")
                .author("Bartłomiej Rychter")
                .year(2014)
                .publishingHouse("Nowa").build();
        Book book3 = new Book().builder()
                .id(3)
                .title("Krzyżacy")
                .author("Henryk Sienkiewicz")
                .year(2011)
                .publishingHouse("Nowy swiat").build();
        Book book4 = new Book().builder()
                .id(4)
                .title("Wiedźmin")
                .author("Andrzej Sapkowski")
                .year(2008)
                .publishingHouse("Nowy świat").build();
        Book book5 = new Book().builder()
                .id(5)
                .title("Kamienie na szaniec")
                .author("Aleksander Kamiński")
                .year(1955)
                .publishingHouse("Nowa").build();
        mapper.save(book1);
        mapper.save(book2);
        mapper.save(book3);
        mapper.save(book4);
        mapper.save(book5);
    }

    public static void putReaders(DynamoDBMapper mapper) {
        Reader reader = new Reader().builder()
                .id(1)
                .name("Adam")
                .surname("Kowalski")
                .age(20)
                .borrowedBooks(new ArrayList<>())
                .build();
        Reader reader2 = new Reader().builder()
                .id(2)
                .name("Marek")
                .surname("Jakubowski")
                .age(25)
                .borrowedBooks(new ArrayList<>())
                .build();
        Reader reader3 = new Reader().builder()
                .id(3)
                .name("Szymon")
                .surname("Woźniak")
                .age(40)
                .borrowedBooks(new ArrayList<>())
                .build();
        Reader reader4 = new Reader().builder()
                .id(4)
                .name("Jakub")
                .surname("Mikołajczak")
                .age(50)
                .borrowedBooks(new ArrayList<>())
                .build();
        Reader reader5 = new Reader().builder()
                .id(5)
                .name("Mirosław")
                .surname("Klose")
                .age(60)
                .borrowedBooks(new ArrayList<>())
                .build();
        mapper.save(reader);
        mapper.save(reader2);
        mapper.save(reader3);
        mapper.save(reader4);
        mapper.save(reader5);
    }

    public static void putLibraries(DynamoDBMapper mapper) {
        Library library = new Library().builder()
                .id(1)
                .name("Biblioteka wojewodzka")
                .address("ul Jagiellońska 102/3")
                .city("Kielce")
                .build();
        Library library2 = new Library().builder()
                .id(2)
                .name("Biblioteka miejska")
                .address("ul Grunwaldzka 33/44")
                .city("Warszawa")
                .build();
        Library library3 = new Library().builder()
                .id(3)
                .name("Biblioteka dla dzieci")
                .address("ul Mielczarskiego 44/22")
                .city("Gdańsk")
                .build();
        mapper.save(library);
        mapper.save(library2);
        mapper.save(library3);
    }
}
