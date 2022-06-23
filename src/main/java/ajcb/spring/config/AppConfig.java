package ajcb.spring.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import ajcb.spring.dao.JdbcProductDao;

@ComponentScan(basePackages = { "ajcb.spring.dao" })
@Configuration
@PropertySource("classpath:jdbc.properties")
public class AppConfig {
	private static final int INIT_SIZE = 10, MAX_SIZE = 100, MAX_WAIT = 500, MAX_IDLE = 50, MIN_IDLE = 2;

	@Value("${server_address}")
	private String serverAddress;
	@Value("${port}")
	private String port;
	@Value("${db_name}")
	private String dbName;
	@Value("${server_name}")
	private String serverName;
	@Value("${user}")
	private String username;
	@Value("${password}")
	private String password;

	/*
	 * //option 1
	 * 
	 * @Bean public JDBCProductDao jdbcDao() throws SQLException { JDBCProductDao
	 * dao = new JDBCProductDao(); dao.setConnection(createConnection()); return
	 * dao; }
	 */

	/*
	 * //option2
	 * 
	 * @Bean public JDBCProductDao jdbcDao(Connection createConnection) throws
	 * SQLException { //injection JDBCProductDao dao = new JDBCProductDao();
	 * dao.setConnection(createConnection);//manual wiring return dao; }
	 */

	// option3
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		// credentials
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setUrl(url());

		// preferences
		dataSource.setInitialSize(INIT_SIZE);
		dataSource.setMaxTotal(MAX_SIZE);
		dataSource.setMaxWaitMillis(MAX_WAIT);
		dataSource.setMaxIdle(MAX_IDLE);
		dataSource.setMinIdle(MIN_IDLE);

		return dataSource;
	}

	@Bean
	public Connection conncetion() throws SQLException {
		return DriverManager.getConnection(url(), username, password);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	private String url() {
		return "jdbc:sqlserver://" + serverAddress + ":" + port + ";databaseName=" + dbName + ";servername="
				+ serverName + ";encrypt=false;";
	}

}
