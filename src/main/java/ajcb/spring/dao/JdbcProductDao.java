package ajcb.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ajbc.spring.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component(value = "jdbcDao")
@Getter
@Setter
@NoArgsConstructor
public class JdbcProductDao implements ProductDao {

	@Autowired(required = false)
	private Connection connection;

	@Autowired(required = false)
	private DataSource dataSource;

	// add a connection to db
	@Override
	public long count() {

		String sql = "select count(*) from Products";
		try (Connection conn = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private Connection createConnection() throws SQLException {
		if (dataSource != null)
			return dataSource.getConnection();
		if (connection == null || connection.isClosed())
			throw new RuntimeException("connection closed");
		return connection;
	}

}
