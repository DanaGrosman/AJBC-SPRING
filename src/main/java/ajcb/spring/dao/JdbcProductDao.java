package ajcb.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ajbc.spring.models.Category;
import ajbc.spring.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component(value = "jdbcDao")
@Getter
@Setter
@NoArgsConstructor
public class JdbcProductDao implements ProductDao {
//
//	@Autowired(required = false)
//	private Connection connection;
//
//	@Autowired(required = false)
//	private DataSource dataSource;

	@Autowired
	private JdbcTemplate template;

	RowMapper<Product> rowMapper = (rs, rowNum) -> {
		Product prod = new Product();
		prod.setProductId(rs.getInt(1));
		prod.setProductName(rs.getString(2));
		prod.setSupplierId(rs.getInt(3));
		prod.setCategoryId(rs.getInt(4));
		prod.setQuantityPerUnit(rs.getString(5));
		prod.setUnitPrice(rs.getDouble(6));
		prod.setUnitsInStock(rs.getInt(7));
		prod.setUnitsOnOrder(rs.getInt(8));
		prod.setReorderLevel(rs.getInt(9));
		prod.setDiscontinued(rs.getInt(10));
		return prod;
	};

	// CRUD
	@Override
	public void addProduct(Product product) throws DaoException {
		String sql = "INSERT INTO Products(ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, "
				+ "UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int rowsAffected = template.update(sql, product.getProductName(), product.getSupplierId(),
				product.getCategoryId(), product.getQuantityPerUnit(), product.getUnitPrice(),
				product.getUnitsInStock(), product.getUnitsOnOrder(), product.getReorderLevel(),
				product.getDiscontinued());
		System.out.println(rowsAffected);
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		String sql = "update Products SET ProductName = ?, SupplierID = ?, CategoryID = ?, QuantityPerUnit = ?, UnitPrice = ?, "
				+ "UnitsInStock = ?, UnitsOnOrder = ?, ReorderLevel = ?, Discontinued = ? WHERE ProductId = ?";
		int rowsAffected = template.update(sql, product.getProductName(), product.getSupplierId(),
				product.getCategoryId(), product.getQuantityPerUnit(), product.getUnitPrice(),
				product.getUnitsInStock(), product.getUnitsOnOrder(), product.getReorderLevel(),
				product.getDiscontinued(), product.getProductId());
		System.out.println(rowsAffected);
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		String sql = "select * from products where productId = ?";
		Product product = template.queryForObject(sql, rowMapper, productId);
		return product;
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		String sql = "delete from products where productId = ?";
		int rowsAffected = template.update(sql, productId);
		System.out.println(rowsAffected);
	}

//	// add a connection to db
//	@Override
//	public long count() {
//
//		String sql = "select count(*) from Products";
//		try (Connection conn = createConnection();
//				PreparedStatement statement = connection.prepareStatement(sql);
//				ResultSet resultSet = statement.executeQuery();) {
//
//			if (resultSet.next()) {
//				return resultSet.getLong(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	private Connection createConnection() throws SQLException {
//		if (dataSource != null)
//			return dataSource.getConnection();
//		if (connection == null || connection.isClosed())
//			throw new RuntimeException("connection closed");
//		return connection;
//	}

}
