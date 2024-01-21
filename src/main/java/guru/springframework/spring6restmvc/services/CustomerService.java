package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;

import java.util.UUID;

public interface CustomerService {
    Iterable<Customer> findCustomers();
    Customer getCustomerById(UUID id);
}
