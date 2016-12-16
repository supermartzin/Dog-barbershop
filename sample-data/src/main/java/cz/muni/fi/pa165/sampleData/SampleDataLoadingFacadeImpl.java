package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.DAOException;
import cz.muni.fi.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Populates database with sample data.
 *
 * @author Dominik Gmiterko
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DogService dogService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private OrderService orderService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() throws DAOException {

        Address address1 = address("Pellentesque, Avenue", 178, "Montemignaio", 44162, "Ukraine");
        Address address2 = address("Orci St.", 202, "Milano", 539, "Ghana");
        Address address3 = address("Enim. St.", 271, "Viersel", 960555, "Luxembourg");
        Address address4 = address("Lorem St.", 218, "Goslar", 99582, "Saint Vincent and The Grenadines");

        Customer customer1 = customer("gravida", "VNX30UMV9XO", "Christian", "Bennett", address1, "gravida@example.com", "226-3333");
        Customer customer2 = customer("felis", "WFB17KJR9XI", "Kylie", "Kent", address2, "felis@example.com", "455-6193");
        Customer customer3 = customer("cubilia", "XVV78UIU6BS", "Judah", "Oneil", address3, "cubilia@example.com", "122-5172");
        Customer customer4 = customer("suscipit", "IID86NZH3YM", "Damian", "Walton", address4, "suscipit@example.com", "930-4139");

        Employee employee1 = employee("admin", "admin", "Admin", "Admin", address1, "admin@example.com", "1111", new BigDecimal("50000"));
        Employee employee2 = employee("augue", "UYB41PXD9KX", "Blaine", "Wall", address2, "augue@example.com", "1-319-862-1855", new BigDecimal("4200"));
        Employee employee3 = employee("justo", "NBN94QKZ5RD", "Suki", "Nixon", address3, "justo@example.com", "1-746-355-4541", new BigDecimal("5100"));
        Employee employee4 = employee("accumsan", "QDJ51JZG6VB", "Ahmed", "Morgan", address4, "accumsan@example.com", "908-4474", new BigDecimal("38300"));

        Dog dog1 = dog("Barbarino", "Bracco Italiano", 5, customer4);
        Dog dog2 = dog("BigDipper", "Nova Scotia Duck Tolling Retriever", 15, customer3);
        Dog dog3 = dog("Skyrocket", "Welsh Springer Spaniel", 7, customer4);
        Dog dog4 = dog("Velcro", "West Highland White Terrier", 8, customer3);
        Dog dog5 = dog("Maggie", "Pekingese", 3, customer1);
        Dog dog6 = dog("Hawk", "Akita", 7, customer2);
        Dog dog7 = dog("Wizkid", "Plott", 3, customer1);
        Dog dog8 = dog("Sleestack", "English Toy Spaniel", 12, customer3);
        Dog dog9 = dog("Menage", "Alaskan Malamute", 10, customer3);
        Dog dog10 = dog("Funky", "Ibizan Hound", 7, customer3);

        Service service1 = service("Bath", 30, new BigDecimal("20.00"));
        Service service2 = service("Clipper pack", 15, new BigDecimal("12.00"));
        Service service3 = service("Nails Trimmed", 10, new BigDecimal("11.00"));
        Service service4 = service("Nails Dremmeled", 10, new BigDecimal("15.00"));
        Service service5 = service("Scaling", 40, new BigDecimal("16.00"));
        Service service6 = service("Teeth brushed", 20, new BigDecimal("11.00"));
        Service service7 = service("Grooming", 50, new BigDecimal("20.00"));

        order(dog1, service1);
        order(dog8, service2);
        order(dog1, service4);
        order(dog2, service5);
        order(dog8, service4);
        order(dog9, service6);
        order(dog9, service5);
        order(dog7, service3);
        order(dog7, service1);
        order(dog7, service6);
        order(dog10, service4);
        order(dog5, service5);
        order(dog5, service5);
        order(dog3, service5);
        order(dog2, service6);
        order(dog5, service3);
        order(dog2, service2);
        order(dog7, service2);
        order(dog4, service1);
        order(dog1, service4);
    }

    private Address address(String street, int number, String city, int postalCode, String country) {
        Address address = new Address(street, number, city, postalCode, country);
        return address;
    }

    private Customer customer(String username, String password, String firstName,
                             String lastName, Address address, String email, String phone) throws DAOException {
        Customer customer = new Customer(username, password, firstName, lastName, address, email, phone);
        customerService.create(customer);
        return customer;
    }

    private Employee employee(String username, String password, String firstName,
                              String lastName, Address address, String email, String phone, BigDecimal salary) throws DAOException {
        Employee employee = new Employee(username, password, firstName, lastName, address, email, phone, salary);
        employeeService.create(employee);
        return employee;
    }

    private Dog dog(String name, String breed, int age, Customer customer) throws DAOException {
        Dog dog = new Dog(name, breed, age, customer);
        dogService.create(dog);
        return dog;
    }

    private Service service(String title, int length, BigDecimal price) throws DAOException {
        Service service = new Service(title, length, price);
        serviceService.create(service);
        return service;
    }

    private Order order(Dog dog, Service service) throws DAOException {

        LocalDateTime time = timeInRange(LocalDateTime.now().minusDays(50), LocalDateTime.now());

        Order order = new Order(time, dog, service);
        orderService.create(order);
        return order;
    }

    private static LocalDateTime timeInRange(LocalDateTime from, LocalDateTime to) {
        long d = to.toEpochSecond(ZoneOffset.UTC) - from.toEpochSecond(ZoneOffset.UTC);
        return from.plusSeconds((int)Math.random() * d);
    }
}
