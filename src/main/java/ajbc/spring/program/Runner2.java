package ajbc.spring.program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ajbc.spring.models.Category;
import ajbc.spring.models.Product;
import ajcb.spring.config.AppConfig;
import ajcb.spring.dao.DaoException;
import ajcb.spring.dao.JdbcProductDao;
import ajcb.spring.dao.ProductDao;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner2 {

	public static void main(String[] args) throws DaoException {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		JdbcTemplate template = context.getBean(JdbcTemplate.class);

		RowMapper<Category> rowMapper = new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category();
				category.setCategoryId(rs.getInt(1));
				category.setCategoryName(rs.getString("categoryName"));
				category.setDescription(rs.getString("description"));
				category.setPicture(rs.getBytes("picture"));
				return category;
			}
		};

		// CRUD
//		insertNewShiper(template);
//		updateShipperPhone(template, 4, "(012) 345-6789");
//		printNumProducts(template);
//		printShipperName(template, 4);
//		printCityOfCustomerByID(template, "FISSA");
//		printProductDetails(template, 3);
//		printAnyOrderOfEmployeeById(template, 5);
//		printAllShippers(template);
//		printAllShippersNames(template);
//		printCategoryById(template, rowMapper, 5);
//		printAllCategiries(template, rowMapper);

		JdbcProductDao jdbcProductDao = context.getBean(JdbcProductDao.class);
//		Product product = new Product();
//		product.setCategoryId(5);
//		product.setDiscontinued(0);
//		product.setSupplierId(2);
//		product.setProductName("Milk");
//		product.setQuantityPerUnit("5");
//		product.setReorderLevel(4);
//		product.setUnitPrice(20.4);
//		product.setUnitsInStock(10);
//		product.setUnitsOnOrder(2);
//		jdbcProductDao.addProduct(product);
//		product.setProductName("Bamba");
//		product.setProductId(78);
//		jdbcProductDao.updateProduct(product);
//		System.out.println(jdbcProductDao.getProduct(79));
		jdbcProductDao.deleteProduct(80);
	}

	private static void printAllCategiries(JdbcTemplate template, RowMapper<Category> rowMapper) {
		String sql = "select * from categories";
		List<Category> categories = template.query(sql, rowMapper);
		categories.forEach(System.out::println);
	}

	private static void printCategoryById(JdbcTemplate template, RowMapper<Category> rowMapper, int id) {
		String sql = "select * from categories where categoryId = ?";
		Category category = template.queryForObject(sql, rowMapper, id);
		System.out.println(category);
	}

	private static void printAllShippersNames(JdbcTemplate template) {
		String sql = "select companyName from shippers";
		List<String> data = template.queryForList(sql, String.class);
		data.forEach(System.out::println);
	}

	private static void printAllShippers(JdbcTemplate template) {
		String sql = "select * from shippers";
		List<Map<String, Object>> shippers = template.queryForList(sql);
		shippers.forEach(System.out::println);
	}

	private static void printAnyOrderOfEmployeeById(JdbcTemplate template, int employeeId) {
		String sql = "select * from orders where employeeId = ?";
		List<Map<String, Object>> orders = template.queryForList(sql, employeeId);
		orders.forEach(System.out::println);
	}

	private static void printProductDetails(JdbcTemplate template, int id) {
		String sql = "select * from products where productId = ?";
		Map<String, Object> product = template.queryForMap(sql, id);
		System.out.println(product);
	}

	private static void printCityOfCustomerByID(JdbcTemplate template, String id) {
		String sql = "select city from customers where customerId = ?";
		String city = template.queryForObject(sql, String.class, id);
		System.out.println(city);
	}

	private static void printShipperName(JdbcTemplate template, int id) {
		String sql = "select companyName from shippers where shipperId = ?";
		String name = template.queryForObject(sql, String.class, id);
		System.out.println(name);
	}

	private static void printNumProducts(JdbcTemplate template) {
		String sql = "select count(*) from products";
		int count = template.queryForObject(sql, int.class);
		System.out.println(count);
	}

	private static void updateShipperPhone(JdbcTemplate template, int id, String phone) {
		String sql = "update shippers SET phone = ? WHERE shipperId = ?";
		int rowsAffected = template.update(sql, phone, id);
		System.out.println(rowsAffected);
	}

	private static void insertNewShiper(JdbcTemplate template) {
		String sql = "insert into shippers values (?,?)";
		int rowsAffected = template.update(sql, "Dana", "123-456-789");
		System.out.println(rowsAffected);
	}

}
