package nagarro.jpa.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author alperkopuz
 * DataSourceConfig class used to establish the embedded db called acconuntssdb inside the workspace
 */
@Configuration
public class DataSourceConfig {

    /**
     * Creates Data source to establish a connection with the embedded db.
     *
     * @return
     * @throws Exception
     */
    @Bean
    public DataSource createDataSource() throws Exception {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        String dir = System.getProperty("user.dir");
        ds.setJdbcUrl("jdbc:ucanaccess://" + dir + "//src//main//resources//accountsdb.accdb;showSchema=true");
        ds.setDriverClass("net.ucanaccess.jdbc.UcanaccessDriver");
        return ds;
    }

}
