package org.ssglobal.training.codes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.service.impl.AdministratorServiceImpl;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;

@Path("/admin")
@RequiredArgsConstructor
public class AdministratorController {
	
	@Autowired
	private AdministratorServiceImpl service;
	
	
	@GET
	@Path("/get")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response selectAllAdministrator() {
		List<Administrator> administrators = service.selectAllAdministrators();
		GenericEntity<List<Administrator>> administratorList = null;
		try {
			if (!administrators.isEmpty()) {
				administratorList = new GenericEntity<>(administrators) {};
				return Response.ok(administratorList).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

}
