package org.ssglobal.training.codes.configurations;
import javax.sql.DataSource;

import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.SessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class RestConfig extends ResourceConfig {

	public RestConfig() {
		packages("org.ssglobal.training.codes.controller");
	}
}
