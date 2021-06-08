package utility;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import dao.CustomerDao;
import dao.TravelAgencyDao;
import dao.VoyageDao;
import mapper.*;
import model.Address;
import model.Customer;
import model.TravelAgency;
import model.Voyage;

public class PutUtility {
    public static void putCustomers(CqlSession session) {
        CustomerMapper customerMapper = new CustomerMapperBuilder(session).build();
        CustomerDao dao = customerMapper.customerDao(CqlIdentifier.fromCql("world"));
        Address address = new Address().builder()
                .street("Jagiellońska")
                .houseNumber(103)
                .flatNumber(20)
                .build();
        Customer customer = new Customer().builder()
                .id(1)
                .name("Jan")
                .surname("Wybicki")
                .age(50)
                .address(address)
                .build();
        Customer customer2 = new Customer().builder()
                .id(2)
                .name("Karol")
                .surname("Marciniak")
                .age(25)
                .address(address)
                .build();
        Customer customer3 = new Customer().builder()
                .id(3)
                .name("Marek")
                .surname("Szkolnikowski")
                .age(40)
                .address(address)
                .build();
        dao.save(customer);
        dao.save(customer2);
        dao.save(customer3);
    }

    public static void putVoyage(CqlSession session) {
        VoyageMapper voyageMapper = new VoyageMapperBuilder(session).build();
        VoyageDao voyageDao = voyageMapper.voyageDao(CqlIdentifier.fromCql("world"));
        Voyage voyage = new Voyage().builder()
                .id(1)
                .destination("Zanzibar")
                .price(10000)
                .days(7)
                .build();
        Voyage voyage2 = new Voyage().builder()
                .id(2)
                .destination("Madagaskar")
                .price(20000)
                .days(14)
                .build();
        Voyage voyage3 = new Voyage().builder()
                .id(3)
                .destination("Malediwy")
                .price(16000)
                .days(11)
                .build();
        voyageDao.save(voyage);
        voyageDao.save(voyage2);
        voyageDao.save(voyage3);
    }


    public static void putTravelAgencies(CqlSession session) {
        TravelAgencyMapper travelAgencyMapper = new TravelAgencyMapperBuilder(session).build();
        TravelAgencyDao travelAgencyDao = travelAgencyMapper.travelAgencyDao(CqlIdentifier.fromCql("world"));
        Address address = new Address().builder()
                .street("Mielczarskiego")
                .flatNumber(202)
                .houseNumber(20)
                .build();
        TravelAgency travelAgency = new TravelAgency().builder()
                .id(1)
                .name("Ikar")
                .address(address)
                .build();
        TravelAgency travelAgency2 = new TravelAgency().builder()
                .id(2)
                .name("Lot")
                .address(address)
                .build();
        TravelAgency travelAgency3 = new TravelAgency().builder()
                .id(3)
                .name("Kreta")
                .address(address)
                .build();
        travelAgencyDao.save(travelAgency);
        travelAgencyDao.save(travelAgency2);
        travelAgencyDao.save(travelAgency3);
    }
}
