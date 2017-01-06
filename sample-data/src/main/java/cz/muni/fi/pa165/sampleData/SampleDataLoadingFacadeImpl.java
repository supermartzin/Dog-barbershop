package cz.muni.fi.pa165.sampleData;

import cz.muni.fi.pa165.entities.*;
import cz.muni.fi.pa165.exceptions.FacadeException;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.service.*;
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

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final DogService dogService;
    private final ServiceService serviceService;
    private final OrderService orderService;

    public SampleDataLoadingFacadeImpl(CustomerService customerService, EmployeeService employeeService,
                                       DogService dogService, ServiceService serviceService, OrderService orderService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.dogService = dogService;
        this.serviceService = serviceService;
        this.orderService = orderService;
    }

    @Override
    public void loadData() throws FacadeException {
        Address address1 = createAddress("Pellentesque, Avenue", 178, "Montemignaio", 44162, "Ukraine");
        Address address2 = createAddress("Orci St.", 202, "Milano", 539, "Ghana");
        Address address3 = createAddress("Enim. St.", 271, "Viersel", 960555, "Luxembourg");
        Address address4 = createAddress("Lorem St.", 218, "Goslar", 99582, "Saint Vincent and The Grenadines");

        Customer customer1 = createCustomer("gravida", "VNX30UMV9XO", "Christian", "Bennett", address1, "gravida@example.com", "226-3333");
        Customer customer2 = createCustomer("felis", "WFB17KJR9XI", "Kylie", "Kent", address2, "felis@example.com", "455-6193");
        Customer customer3 = createCustomer("cubilia", "XVV78UIU6BS", "Judah", "Oneil", address3, "cubilia@example.com", "122-5172");
        Customer customer4 = createCustomer("suscipit", "IID86NZH3YM", "Damian", "Walton", address4, "suscipit@example.com", "930-4139");

        Employee employee1 = createEmployee("admin", "123456", "Admin", "Admin", address1, "admin@example.com", "1111", new BigDecimal("50000"));
        Employee employee2 = createEmployee("augue", "UYB41PXD9KX", "Blaine", "Wall", address2, "augue@example.com", "1-319-862-1855", new BigDecimal("4200"));
        Employee employee3 = createEmployee("justo", "NBN94QKZ5RD", "Suki", "Nixon", address3, "justo@example.com", "1-746-355-4541", new BigDecimal("5100"));
        Employee employee4 = createEmployee("accumsan", "QDJ51JZG6VB", "Ahmed", "Morgan", address4, "accumsan@example.com", "908-4474", new BigDecimal("38300"));

        Dog dog1 = createDog("Barbarino", "Bracco Italiano", 5, customer4);
        Dog dog2 = createDog("BigDipper", "Nova Scotia Duck Tolling Retriever", 15, customer3);
        Dog dog3 = createDog("Skyrocket", "Welsh Springer Spaniel", 7, customer4);
        Dog dog4 = createDog("Velcro", "West Highland White Terrier", 8, customer3);
        Dog dog5 = createDog("Maggie", "Pekingese", 3, customer1);
        Dog dog6 = createDog("Wizkid", "Plott", 3, customer1);
        Dog dog7 = createDog("Sleestack", "English Toy Spaniel", 12, customer3);
        Dog dog8 = createDog("Menage", "Alaskan Malamute", 10, customer3);
        Dog dog9 = createDog("Funky", "Ibizan Hound", 7, customer3);
        createDog("Hawk", "Akita", 7, customer2);

        Service service1 = createService("Bath", 30, new BigDecimal("20.00"));
        Service service2 = createService("Clipper pack", 15, new BigDecimal("12.00"));
        Service service3 = createService("Nails Trimmed", 10, new BigDecimal("11.00"));
        Service service4 = createService("Nails Dremmeled", 10, new BigDecimal("15.00"));
        Service service5 = createService("Scaling", 40, new BigDecimal("16.00"));
        Service service6 = createService("Teeth brushed", 20, new BigDecimal("11.00"));
        createService("Grooming", 50, new BigDecimal("20.00"));

        createOrder(dog1, service1, employee1);
        createOrder(dog7, service2, employee4);
        createOrder(dog1, service4, null);
        createOrder(dog2, service5, employee2);
        createOrder(dog7, service4, employee3);
        createOrder(dog8, service6, null);
        createOrder(dog8, service5, employee3);
        createOrder(dog6, service3, employee1);
        createOrder(dog6, service1, null);
        createOrder(dog6, service6, employee3);
        createOrder(dog9, service4, employee1);
        createOrder(dog5, service5, null);
        createOrder(dog5, service5, employee1);
        createOrder(dog3, service5, null);
        createOrder(dog2, service6, employee2);
        createOrder(dog5, service3, employee1);
        createOrder(dog2, service2, null);
        createOrder(dog6, service2, employee4);
        createOrder(dog4, service1, employee4);
        createOrder(dog1, service4, employee2);
    }

    @Transactional
    private Address createAddress(String street, int number, String city, int postalCode, String country) {
        return new Address(street, number, city, postalCode, country);
    }

    @Transactional
    private Customer createCustomer(String username, String password, String firstName,
                                    String lastName, Address address, String email, String phone) throws FacadeException {
        try {
            Customer customer = new Customer(username, password, firstName, lastName, address, email, phone);

            customerService.create(customer);

            return customer;
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    @Transactional
    private Employee createEmployee(String username, String password, String firstName,
                                    String lastName, Address address, String email, String phone, BigDecimal salary) throws FacadeException {
        try {
            Employee employee = new Employee(username, password, firstName, lastName, address, email, phone, salary);

            employeeService.create(employee);

            return employee;
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    @Transactional
    private Dog createDog(String name, String breed, int age, Customer customer) throws FacadeException {
        try {
            Dog dog = new Dog(name, breed, age, customer);
            customer.addDog(dog);

            dogService.create(dog);

            return dog;
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    @Transactional
    private Service createService(String title, int length, BigDecimal price) throws FacadeException {
        try {
            Service service = new Service(title, length, price);

            serviceService.create(service);

            return service;
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    @Transactional
    private Order createOrder(Dog dog, Service service, Employee employee) throws FacadeException {
        LocalDateTime time = getTimeInRange(LocalDateTime.now().minusDays(50), LocalDateTime.now());

        try {
            Order order = new Order(time, dog, service);
            order.setEmployee(employee);

            orderService.create(order);

            return order;
        } catch (ServiceException sEx) {
            throw new FacadeException(sEx);
        }
    }

    private static LocalDateTime getTimeInRange(LocalDateTime from, LocalDateTime to) {
        long d = to.toEpochSecond(ZoneOffset.UTC) - from.toEpochSecond(ZoneOffset.UTC);

        return from.plusSeconds((long) (Math.random() * d));
    }
}
