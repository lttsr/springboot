package sample;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import jakarta.persistence.EntityManagerFactory;
import sample.context.orm.DefaultRepository;
import sample.context.orm.OrmRepository;

@Configuration
public class DbConfig {

    @Configuration
    static class DefaultDbConfig {

        @Bean
        @Primary
        OrmRepository defaultRepository() {
            return DefaultRepository.of();
        }

        @Bean(name = DefaultRepository.BeanNameDs, destroyMethod = "close")
        @Primary
        DataSource dataSource(AppProperties props) {
            System.out.println(props);
            return props.getDatasource().getApp().dataSource();
        }

        @Bean(name = DefaultRepository.BeanNameEmf)
        @Primary
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
                AppProperties props,
                @Qualifier(DefaultRepository.BeanNameDs) final DataSource dataSource) {
            return props.getDatasource().getApp().entityManagerFactoryBean(dataSource);
        }

        @Bean(name = DefaultRepository.BeanNameTx)
        @Primary
        JpaTransactionManager transactionManager(
                AppProperties props,
                @Qualifier(DefaultRepository.BeanNameEmf) final EntityManagerFactory emf) {
            return props.getDatasource().getApp().transactionManager(emf);
        }

    }
}