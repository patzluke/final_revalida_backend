package org.ssglobal.training.codes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.FarmingTip;
import org.ssglobal.training.codes.service.AdministratorService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/admin")
public class AdministratorController {
	
	@Autowired
	private AdministratorService service;

	@GET
	@Path("/get/farmingtips")
    @Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllFarmingTip() {
		List<FarmingTip> farmingTips= service.selectAllFarmingTip();
		GenericEntity<List<FarmingTip>> farmingTipsEntity = null;
		try {
			if (!farmingTips.isEmpty()) {
				farmingTipsEntity = new GenericEntity<>(farmingTips) {};
				return Response.ok(farmingTipsEntity).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Path("/insert/farmingtips")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoFarmingTip(FarmingTip payload) {
		payload.setFarmingTipId(null);
		payload.setDateCreated(LocalDateTime.now());
		System.out.println(payload);
		FarmingTip farmingTip = service.insertIntoFarmingTip(payload);
		GenericEntity<FarmingTip> farmingTipEntity = null;
		
		try {
			if (farmingTip != null) {
				farmingTipEntity = new GenericEntity<>(farmingTip) {};
				return Response.ok(farmingTipEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("/update/farmingtips")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateIntoFarmingTip(FarmingTip payload) {
		FarmingTip farmingTip = service.updateIntoFarmingTip(payload);
		GenericEntity<FarmingTip> farmingTipEntity = null;
		
		try {
			if (farmingTip != null) {
				farmingTipEntity = new GenericEntity<>(farmingTip) {};
				return Response.ok(farmingTipEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@DELETE
	@Path("/delete/farmingtips/{farmingTipId}")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response deleteFarmingTip(@PathParam(value = "farmingTipId") Integer farmingTipId) {
		FarmingTip farmingTip = service.deleteFarmingTip(farmingTipId);
		GenericEntity<FarmingTip> farmingTipEntity = null;
		
		try {
			if (farmingTip != null) {
				farmingTipEntity = new GenericEntity<>(farmingTip) {};
				return Response.ok(farmingTipEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
