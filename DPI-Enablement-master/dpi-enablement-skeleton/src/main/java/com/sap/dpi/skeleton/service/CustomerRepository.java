package com.sap.dpi.skeleton.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sap.dpi.skeleton.model.Customer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerRepository {

    private final Map<String, Customer> database;

    public CustomerRepository() {
        database = new ConcurrentHashMap<String, Customer>();
    }

    public Collection<Customer> getAll() {
        return database.values();
    }

    public Optional<Customer> get(String customerID) {
        return Optional.ofNullable(database.get(customerID));
    }

    public Optional<Customer> upsert(Customer customer) {
        if (customer != null && customer.getCustomerID() != null) {
            database.put(customer.getCustomerID(), customer);
            return Optional.of(customer);
        }

        return Optional.empty();
    }

    public void delete(String customerID) {
        database.remove(customerID);
    }

    public Collection<Customer> search(Map<String, String> searchParameter) {
        Stream<Customer> customers = getAll().stream();
        if (searchParameter.containsKey("customerID")) {
            String customerID = searchParameter.get("customerID");
            customers = customers.filter(c -> c.getCustomerID().equalsIgnoreCase(customerID));
        }

        if (searchParameter.containsKey("firstName")) {
            String firstName = searchParameter.get("firstName");
            customers = customers.filter(c -> c.getFirstName().equalsIgnoreCase(firstName));
        }

        if (searchParameter.containsKey("lastName")) {
            String lastName = searchParameter.get("lastName");
            customers = customers.filter(c -> c.getLastName().equalsIgnoreCase(lastName));
        }

        if (searchParameter.containsKey("email")) {
            String email = searchParameter.get("email");
            customers = customers.filter(c -> c.getEmail().equalsIgnoreCase(email));
        }

        if (searchParameter.containsKey("birthday")) {
            String birthday = searchParameter.get("birthday");
            try {
                LocalDate birthDate = LocalDate.parse(birthday);
                customers = customers.filter(c -> c.getBirthday().equals(birthDate));
            } catch (DateTimeParseException e) {
                log.error("invalid birthday", e);
            }
        }
        return customers.collect(Collectors.toList());
    }
}
