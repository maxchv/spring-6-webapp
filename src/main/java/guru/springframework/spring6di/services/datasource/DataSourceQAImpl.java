package guru.springframework.spring6di.services.datasource;

import guru.springframework.spring6di.services.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("qa")
@Service
public class DataSourceQAImpl implements DataSource {
    @Override
    public String getDataSource() {
        return "QA DataSource";
    }
}
