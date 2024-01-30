package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void listAllCustomers() {
        List<CustomerDTO> dtos = customerController.listAllCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void getNotExistingCustomerById() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }
}