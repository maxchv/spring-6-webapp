package guru.springframework.spring6di.controllers;

import guru.springframework.spring6di.services.DataSource;
import org.springframework.stereotype.Controller;

@Controller
public class DataSourceController {

    private final DataSource dataSource;

    public DataSourceController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource.getDataSource();
    }
}
