package org.ssglobal.training.codes;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import org.ssglobal.training.codes.controller.AuthenticateController;

import jakarta.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class RestConfig extends ResourceConfig {

	public RestConfig() {
		packages("org.ssglobal.training.codes.controller");
	}
}
