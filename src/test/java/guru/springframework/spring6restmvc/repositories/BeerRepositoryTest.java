package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.bootstrap.BoostrapDatabase;
import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() throws Exception {
        new BoostrapDatabase(beerRepository, customerRepository).run(null);
    }

    @Test
    @Order(1)
    void testGetBeer() {
        assertThat(beerRepository.count()).isGreaterThan(0);
        Beer beer = beerRepository.findAll().get(0);
        assertThat(beer.getId()).isNotNull();
        assertThat(beer.getVersion()).isNotNull();
    }

    @Test
    @Order(2)
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My Beer")
                .build());

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}