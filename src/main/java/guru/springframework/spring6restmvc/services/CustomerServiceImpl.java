package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, Customer> customers = Stream.of(
                    Customer.builder()
                            .id(UUID.randomUUID())
                            .customerName("First Customer")
                            .build(),
                    Customer.builder()
                            .id(UUID.randomUUID())
                            .customerName("Second Customer")
                            .build(),
                    Customer.builder()
                            .id(UUID.randomUUID())
                            .customerName("Third Customer")
                            .build()
            ).map(c -> {
                c.setVersion(1);
                c.setCreatedDate(LocalDateTime.now());
                c.setUpdateDate(LocalDateTime.now());
                return c;
            })
            .collect(Collectors.toMap(Customer::getId, c -> c));

    @Override
    public Iterable<Customer> findCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customers.get(id);
    }
}
