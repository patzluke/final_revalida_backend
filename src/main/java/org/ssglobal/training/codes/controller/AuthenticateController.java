package org.ssglobal.training.codes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
public class AuthenticateController {
	
	@GET
	@Path("/get")
    @Produces({ MediaType.APPLICATION_JSON })
	public Response hello() {
		List<Map<String, Object>> sample = new ArrayList<Map<String, Object>>();
		GenericEntity<List<Map<String, Object>>> listDep = null;
		try {
			HashMap<String, Object> patHashMap = new HashMap<String, Object>();
			patHashMap.put("name", "patrick");
			sample.add(patHashMap);
			if (!sample.isEmpty()) {
				listDep = new GenericEntity<>(sample) {};
				return Response.ok(listDep).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
}
