package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;
    
    @Autowired
    CustomerMapper customerMapper;
    
    @Test
    @Transactional
    @Rollback
    void testSaveNewCustomer() {
        var responseEntity = customerController.handlePost(CustomerDTO.builder()
                .name("TEST NAME")
                .build());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().get("Location")).isNotNull();

        URI location = responseEntity.getHeaders().getLocation();
        String[] paths = location.getPath().split("/");

        Customer customer = customerRepository.findById(UUID.fromString(paths[paths.length - 1])).get();
        assertThat(customer).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        final String customerName = "New Name " + UUID.randomUUID();
        customerDTO.setName(customerName);
        customerDTO.setVersion(0);
        customerDTO.setId(null);
        var responseEntity = customerController.updateCustomerByID(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var actualCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(actualCustomer.getName()).isEqualTo(customerName);
    }

    @Test
    void testUpdateByIdNotExistingCustomer() {
        assertThrows(NotFoundException.class, () -> {
           customerController.updateCustomerByID(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.existsById(customer.getId())).isFalse();
    }

    @Test
    void testDeleteByIdNotExistingCustomer() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testPatchCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        final String customerName = "New Name " + UUID.randomUUID();
        customerDTO.setName(customerName);
        customerDTO.setVersion(0);
        customerDTO.setId(null);
        var responseEntity = customerController.patchCustomerById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var actualCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(actualCustomer.getName()).isEqualTo(customerName);
    }

    @Test
    void testPatchByIdNotExistingCustomer() {
        assertThrows(NotFoundException.class, () -> {
            customerController.patchCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testListAll() {
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }
}










