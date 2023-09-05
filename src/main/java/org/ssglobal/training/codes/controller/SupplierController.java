package org.ssglobal.training.codes.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.service.SupplierService;

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

@Path("supplier")
public class SupplierController {

	@Autowired
	private SupplierService service;
	
	@GET
	@Path("/get/supplier/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findOneByUserId(@PathParam(value = "userId") Integer userId) {
		Optional<Supplier> supplierOptional = service.findOneByUserId(userId);
		GenericEntity<Supplier> supplierEntity = null;
		try {
			if (!supplierOptional.isEmpty()) {
				supplierEntity = new GenericEntity<>(supplierOptional.orElse(null)) {
				};
				return Response.ok(supplierEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}	
	
	@PUT
	@Path("/update/supplier")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateSupplierInfo(Map<String, Object> payload) {
		Supplier supplier = service.updateSupplierInfo(payload);
		GenericEntity<Supplier> supplierEntity = null;
		
		try {
			if (supplier != null) {
				supplierEntity = new GenericEntity<>(supplier) {};
				return Response.ok(supplierEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Path("/get/postadvertisement/{supplierId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectPostAdvertisementBySupplierId(@PathParam(value = "supplierId") Integer supplierId) {
		List<PostAdvertisement> advertisements = service.selectPostAdvertisementBySupplierId(supplierId);
		GenericEntity<List<PostAdvertisement>> advertisementsEntity = null;
		try {
			if (!advertisements.isEmpty()) {
				advertisementsEntity = new GenericEntity<>(advertisements) {
				};
				return Response.ok(advertisementsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@POST
	@Path("/insert/postadvertisement")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoPostAdvertisement(Map<String, Object> payload) {
		PostAdvertisement advertisement = service.insertIntoPostAdvertisement(payload);
		GenericEntity<PostAdvertisement> advertisementsEntity = null;
		try {

			if (advertisement != null) {
				advertisementsEntity = new GenericEntity<>(advertisement) {
				};
				return Response.ok(advertisementsEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@PUT
	@Path("/update/postadvertisement")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateIntoPostAdvertisement(Map<String, Object> payload) {
		PostAdvertisement advertisement = service.updateIntoPostAdvertisement(payload);
		GenericEntity<PostAdvertisement> advertisementEntity = null;

		try {
			if (advertisement != null) {
				advertisementEntity = new GenericEntity<>(advertisement) {
				};
				return Response.ok(advertisementEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("/delete/postadvertisement/{postId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response softDeletePostAdvertisement(@PathParam(value = "postId") Integer postId) {
		PostAdvertisement advertisement = service.softDeletePostAdvertisement(postId);
		GenericEntity<PostAdvertisement> advertisementEntity = null;
		try {
			if (advertisement != null) {
				advertisementEntity = new GenericEntity<>(advertisement) {
				};
				return Response.ok(advertisementEntity).build();
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
	
	// Post Advertisement Respones
	@GET
	@Path("/get/postadvertisementresponse/{postId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllPostAdvertisementResponsesByPostId(@PathParam(value = "postId") Integer postId) {
		List<PostAdvertisementResponse> postAdvertisementResponses = service
				.selectAllPostAdvertisementResponsesByPostId(postId);
		GenericEntity<List<PostAdvertisementResponse>> postAdvertisementResponsesEntity = null;
		try {
			if (!postAdvertisementResponses.isEmpty()) {
				postAdvertisementResponsesEntity = new GenericEntity<>(postAdvertisementResponses) {
				};
				return Response.ok(postAdvertisementResponsesEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("/update/postadvertisementresponse")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updatePostAdvertisementResponsesIsAcceptedStatus(Map<String, Object> payload) {
		PostAdvertisementResponse advertisementResponse = service.updatePostAdvertisementResponsesIsAcceptedStatus(payload);
		GenericEntity<PostAdvertisementResponse> advertisementResponsetEntity = null;
		try {
			if (advertisementResponse != null) {
				advertisementResponsetEntity = new GenericEntity<>(advertisementResponse) {
				};
				return Response.ok(advertisementResponsetEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/get/croppayment/{supplierId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllCropPaymentBySupplier(@PathParam(value = "supplierId") Integer supplierId) {
		List<CropPayment> cropPayments = service.selectAllCropPaymentBySupplier(supplierId);
		GenericEntity<List<CropPayment>> cropPaymentsEntity = null;
		try {
			if (!cropPayments.isEmpty()) {
				cropPaymentsEntity = new GenericEntity<>(cropPayments) {
				};
				return Response.ok(cropPaymentsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("/update/croppayment")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateCropPaymentStatus(Map<String, Object> payload) {
		CropPayment cropPayment = service.updateCropPaymentStatus(payload);
		GenericEntity<CropPayment> cropPaymentEntity = null;
		try {
			if (cropPayment != null) {
				cropPaymentEntity = new GenericEntity<>(cropPayment) {
				};
				return Response.ok(cropPaymentEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
