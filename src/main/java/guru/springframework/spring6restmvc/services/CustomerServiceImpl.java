package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Map<UUID, Customer> customers = IntStream.rangeClosed(1, 5)
                    .mapToObj(i ->
                    Customer.builder()
                            .id(UUID.randomUUID())
                            .customerName("Customer " + i)
                            .version(1)
                            .createdDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build()
                    )
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
