package org.ssglobal.training.codes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.service.FarmerService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/farmer")
public class FarmerController {
	
	@Autowired
	private FarmerService service;

	// FarmingTips
	@GET
	@Path("/get/farmercomplaints/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectFarmerComplaints(@PathParam(value = "farmerId") Integer  farmerId) {
		List<FarmerComplaint> farmerComplaints = service.selectFarmerComplaints(farmerId);
		GenericEntity<List<FarmerComplaint>> farmerComplaintsEntity = null;
		try {
			if (!farmerComplaints.isEmpty()) {
				farmerComplaintsEntity = new GenericEntity<>(farmerComplaints) {
				};
				return Response.ok(farmerComplaintsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@POST
	@Path("/insert/farmercomplaints")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoFarmerComplaint(Map<String, Object> payload) {
		FarmerComplaint farmerComplaint = service.insertIntoFarmerComplaint(payload);
		GenericEntity<FarmerComplaint> farmerComplaintsEntity = null;
		
		try {
			if (farmerComplaint != null) {
				farmerComplaintsEntity = new GenericEntity<>(farmerComplaint) {};
				return Response.ok(farmerComplaintsEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
