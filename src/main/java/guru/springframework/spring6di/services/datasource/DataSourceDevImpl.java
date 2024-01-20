package guru.springframework.spring6di.services.datasource;

import guru.springframework.spring6di.services.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dev", "default"})
@Service
public class DataSourceDevImpl implements DataSource {
    @Override
    public String getDataSource() {
        return "Dev DataSource";
    }
}
