package org.ssglobal.training.codes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.Users;
import org.ssglobal.training.codes.service.AuthenticateService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
public class AuthenticateController {
	
	@Autowired
	private AuthenticateService service;
	
	@POST
	@Path("/login")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response searchUserByUsernameAndPassword(Map<String, Object> payload) {
		Map<String, Object> authenticatedUser = service.searchUserByUsernameAndPassword(payload.get("username").toString(), payload.get("password").toString());
		GenericEntity<List<Object>> userEntity = null;
		try {
			if (authenticatedUser != null) {
				List<Object> usertoken = new ArrayList<>();
				String token = service
						.generateToken(Integer.valueOf(authenticatedUser.get("userId").toString()),
									   Integer.valueOf(authenticatedUser.get("userNo").toString()),
									   authenticatedUser.get("username").toString(), 
									   authenticatedUser.get("userType").toString(),
									   Boolean.valueOf(authenticatedUser.get("activeStatus").toString())
									  );
				
				usertoken.add(token);
				userEntity = new GenericEntity<>(usertoken) {};
				return Response.ok(userEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();

	}
	
	@PUT
	@Path("/update/password")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response changePassword(Map<String, Object> payload) {
		System.out.println(payload);
		Users user = service.changePassword(payload.get("password").toString(), payload.get("username").toString());
		GenericEntity<Users> userEntity = null;
		try {
			if (user != null) {
				userEntity = new GenericEntity<>(user) {};
				return Response.ok(userEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();

	}
}
