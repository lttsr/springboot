package sample;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import sample.context.orm.DefaultRepository.DefaultDataSourceProperties;

@ConfigurationProperties("db")
@Data
public class AppProperties {
    private DatasourceProps datasource = new DatasourceProps();

    @Data
    public static class DatasourceProps {
        private DefaultDataSourceProperties app = new DefaultDataSourceProperties();
    }
}