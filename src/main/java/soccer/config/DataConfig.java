package soccer.config;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DataConfig {

    @Autowired
    SessionFactory sessionFactory;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
        edb.setType(EmbeddedDatabaseType.H2);
        edb.addScript("concordia/soccer/database/schema.sql");
        EmbeddedDatabase embeddedDatabase = edb.build();
        return embeddedDatabase;
    }

     @Bean
     LocalSessionFactoryBean sessionFactoryBean(){
         try{
                LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
                lsfb.setDataSource(dataSource());
                lsfb.setPackagesToScan("soccer.entities");
                Properties props = new Properties();
                props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
                lsfb.setHibernateProperties(props);
                lsfb.afterPropertiesSet();
                return lsfb;
            } catch (IOException e) {
                throw new RuntimeException("Hibernate Session Factory Could Not Be Constructed");
            }

    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        System.out.println(sessionFactory);
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}
