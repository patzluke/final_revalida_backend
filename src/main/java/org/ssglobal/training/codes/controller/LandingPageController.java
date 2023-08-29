package org.ssglobal.training.codes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.service.LandingPageService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/landing")
public class LandingPageController {

	@Autowired
	private LandingPageService service;

	@GET
	@Path("/get/postadvertisements")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllPostAdvertisements() {
		List<PostAdvertisement> postAdvertisements = service.selectAllPostAdvertisements();
		GenericEntity<List<PostAdvertisement>> postAdvertisementsEntity = null;
		try {
			if (!postAdvertisements.isEmpty()) {
				postAdvertisementsEntity = new GenericEntity<>(postAdvertisements) {
				};
				return Response.ok(postAdvertisementsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	// Post Crop Specialization
	@GET
	@Path("/get/cropspecialization")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllCropSpecialization() {
		List<CropSpecialization> cropSpecializations = service.selectAllCropSpecialization();
		GenericEntity<List<CropSpecialization>> cropSpecializationsEntity = null;
		try {
			if (!cropSpecializations.isEmpty()) {
				cropSpecializationsEntity = new GenericEntity<>(cropSpecializations) {
				};
				return Response.ok(cropSpecializationsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
