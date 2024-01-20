package guru.springframework.spring6di.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataSourceControllerTest {

    @Autowired
    DataSourceController dataSourceController;

    @Test
    void getDataSource() {
        Assertions.assertEquals(
                "Dev DataSource",
                dataSourceController.getDataSource()
        );
    }
}