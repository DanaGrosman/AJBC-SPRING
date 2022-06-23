package ajbc.spring.program;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajcb.spring.config.AppConfig;
import ajcb.spring.dao.ProductDao;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner {

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// The dependency
		ProductDao dao;

		// The spring container
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println("--------------------------------------");
		dao = context.getBean("jdbcDao", ProductDao.class);
		System.out.println("The number of product in the DB is " + dao.count());
		System.out.println("--------------------------------------");
		System.out.println("-----------------AGAIN----------------");
		System.out.println("The number of product in the DB is " + dao.count());
		context.close();
	}

}
