package org.ssglobal.training.codes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.service.FarmerService;

import jakarta.ws.rs.GET;
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
		List<FarmerComplaint> farmingTips = service.selectFarmerComplaints(farmerId);
		GenericEntity<List<FarmerComplaint>> farmingTipsEntity = null;
		try {
			if (!farmingTips.isEmpty()) {
				farmingTipsEntity = new GenericEntity<>(farmingTips) {
				};
				return Response.ok(farmingTipsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
