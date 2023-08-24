package org.ssglobal.training.codes.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.service.RegistrationService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("registration")
public class RegistrationController {

	@Autowired
	private RegistrationService service;
	
	@POST
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response registerUser(Map<String, Object> payload) {
		System.out.println("payload");
		Object user = service.registerUser(payload);
		GenericEntity<Object> userEntity = null;
		try {
			if (user != null) {
				userEntity = new GenericEntity<>(user) {
				};
				return Response.ok(userEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
