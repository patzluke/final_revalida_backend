package org.ssglobal.training.codes.configurations;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class RestConfig extends ResourceConfig {
	
	public RestConfig() {
		packages("org.ssglobal.training.codes").register(MultiPartFeature.class);
	}
}
