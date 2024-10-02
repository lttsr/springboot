package sample.context.orm;

import javax.sql.DataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Repository of the standard schema. */
public class DefaultRepository extends OrmRepository {
    public static final String BeanNameDs = "dataSource";
    public static final String BeanNameEmf = "entityManagerFactory";
    public static final String BeanNameTx = "transactionManager";

    @PersistenceContext(unitName = BeanNameEmf)
    private EntityManager em;

    public EntityManager em() {
        return em;
    }

    public void em(EntityManager em) {
        this.em = em;
    }

    public static DefaultRepository of() {
        return new DefaultRepository();
    }

    /** Generate a DataSource for the standard schema. */
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class DefaultDataSourceProperties extends OrmDataSourceProperties {
        // username,password含むインスタンス
        private OrmRepositoryProperties jpa = new OrmRepositoryProperties();

        public DataSource dataSource() {
            return super.dataSource();
        }

        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
                final DataSource dataSource) {
            return jpa.entityManagerFactoryBean(BeanNameEmf, dataSource);
        }

        public JpaTransactionManager transactionManager(final EntityManagerFactory emf) {
            return jpa.transactionManager(emf);
        }
    }

}