package app.config;

import java.util.Properties;

import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import app.model.Note;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "app.controller", "app.dao", "app.model", "app.login" })
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory() {

		Properties dbConnectionProperties = new Properties();
		try {
			dbConnectionProperties.load(ClassLoader.getSystemClassLoader()
					.getResourceAsStream("hibernate.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
		configuration.setProperties(dbConnectionProperties);
		configuration.addAnnotatedClass(Note.class);
		configuration.configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		return factory;
	}
	
	 @Bean
	   public DataSource restDataSource() {
	      BasicDataSource dataSource = new BasicDataSource();
	      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	      dataSource.setUrl("jdbc:mysql://localhost:3306/notes");
	      dataSource.setUsername("olga");
	      dataSource.setPassword("olga");
	 
	      return dataSource;
	   }
	
	 @Bean
	    public InternalResourceViewResolver internalResourceViewResolver() {
	        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	        resolver.setPrefix("/jsp/");
	        resolver.setSuffix(".jsp");
	        return resolver;
	    }
	 

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

	}
}
