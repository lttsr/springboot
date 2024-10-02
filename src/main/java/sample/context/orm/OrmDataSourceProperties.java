package sample.context.orm;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.util.StringUtils;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

@Data
public class OrmDataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Properties props = new Properties();

    private int minIdle = 1;
    private int maxPoolSize = 20;
    private boolean validation = true;

    private String validationQuery;

    public String name() {
        return this.getClass().getSimpleName().replaceAll("Properties", "");
    }

    public DataSource dataSource() {
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName("org.postgresql.Driver")
                .url(this.url)
                .username(this.username).password(this.password)
                .build();
        dataSource.setMinimumIdle(minIdle);
        dataSource.setMaximumPoolSize(maxPoolSize);
        if (validation) {
            dataSource.setConnectionTestQuery(validationQuery());
        }
        dataSource.setPoolName(name());
        dataSource.setDataSourceProperties(props);
        return dataSource;
    }

    private String validationQuery() {
        if (StringUtils.hasText(validationQuery)) {
            return validationQuery;
        }
        return DatabaseDriver.fromJdbcUrl(url).getValidationQuery();
    }
}