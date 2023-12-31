package org.ssglobal.training.codes.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.Course;
import org.ssglobal.training.codes.models.CourseEnrolled;
import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.service.FarmerService;

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

@Path("/farmer")
public class FarmerController {
	
	@Autowired
	private FarmerService service;

	
	@GET
	@Path("/get/farmer/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findOneByUserId(@PathParam(value = "userId") Integer userId) {
		Optional<Farmer> farmerOptional = service.findOneByUserId(userId);
		GenericEntity<Farmer> farmerEntity = null;
		try {
			if (!farmerOptional.isEmpty()) {
				farmerEntity = new GenericEntity<>(farmerOptional.orElse(null)) {
				};
				return Response.ok(farmerEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}	
	
	@PUT
	@Path("/update/farmer")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateFarmerInfo(Map<String, Object> payload) {
		Farmer farmer = service.updateFarmerInfo(payload);
		GenericEntity<Farmer> farmerEntity = null;
		
		try {
			if (farmer != null) {
				farmerEntity = new GenericEntity<>(farmer) {};
				return Response.ok(farmerEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
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
	
	@PUT
	@Path("/update/farmercomplaints")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateIntoFarmerComplaint(Map<String, Object> payload) {
		FarmerComplaint updatedComplaint = new FarmerComplaint();
		updatedComplaint.setFarmerComplaintId(Integer.valueOf(payload.get("farmerComplaintId").toString()));
		updatedComplaint.setComplaintTitle(payload.get("complaintTitle").toString());
		updatedComplaint.setComplaintMessage(payload.get("complaintMessage").toString());
		FarmerComplaint farmingComplaint = service.updateIntoFarmerComplaint(updatedComplaint);
		GenericEntity<FarmerComplaint> farmingTipEntity = null;
		
		try {
			if (farmingComplaint != null) {
				farmingTipEntity = new GenericEntity<>(farmingComplaint) {};
				return Response.ok(farmingTipEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@DELETE
	@Path("/delete/farmercomplaints/{farmerComplaintId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response softDeleteFarmerComplaint(@PathParam(value = "farmerComplaintId") Integer  farmerComplaintId) {
		FarmerComplaint farmerComplaints = service.softDeleteFarmerComplaint(farmerComplaintId);
		GenericEntity<FarmerComplaint> farmerComplaintsEntity = null;
		try {
			if (farmerComplaints != null) {
				farmerComplaintsEntity = new GenericEntity<>(farmerComplaints) {
				};
				return Response.ok(farmerComplaintsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	// Sales Report
	@GET
	@Path(value = "/get/farmer-total-sales-by-id/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public double getTotalSalesForFarmer(@PathParam(value = "farmerId") Integer farmerId) {
        return service.calculateTotalSales(farmerId);
    }

	@GET
	@Path(value = "/get/sales-per-month/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Integer> getSalesDataPerMonth(@PathParam(value = "farmerId") Integer farmerId) {
        return service.getSalesDataPerMonth(farmerId);
    }

	@GET
	@Path(value = "/get/customer-count/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Integer countCropOrdersPerYear(@PathParam(value = "farmerId") Integer farmerId) {
        return service.countCropOrdersPerYear(farmerId);
    }
	
	@GET
	@Path(value = "/get/three-recent-sales/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTopThreeRecentSellCropDetails(@PathParam(value = "farmerId") Integer farmerId) {
		List<CropPayment> topThree = service.getTopThreeRecentSellCropDetails(farmerId);
		GenericEntity<List<CropPayment>> topThreeEntity = null;
		try {
			if (!topThree.isEmpty()) {
				topThreeEntity = new GenericEntity<>(topThree) {
				};
				return Response.ok(topThreeEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
    }

	
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
	
	@POST
	@Path("/insert/postadvertisementresponse")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoPostAdvertisementResponse(Map<String, Object> payload) {
		PostAdvertisementResponse response = service.insertIntoPostAdvertisementResponse(payload);
		GenericEntity<PostAdvertisementResponse> responseEntity = null;
		
		try {
			if (response != null) {
				responseEntity = new GenericEntity<>(response) {};
				return Response.ok(responseEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/get/course")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllCourses() {
		List<Course> courses = service.selectAllCourses();
		GenericEntity<List<Course>> coursesEntity = null;
		try {
			if (!courses.isEmpty()) {
				coursesEntity = new GenericEntity<>(courses) {
				};
				return Response.ok(coursesEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Path("/get/courseenrolled/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllCoursesEnrolledByFarmer(@PathParam(value = "farmerId") Integer farmerId) {
		List<CourseEnrolled> courseEnrolled = service.selectAllCoursesEnrolledByFarmer(farmerId);
		GenericEntity<List<CourseEnrolled>> courseEnrolledEntity = null;
		try {
			if (!courseEnrolled.isEmpty()) {
				courseEnrolledEntity = new GenericEntity<>(courseEnrolled) {
				};
				return Response.ok(courseEnrolledEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@POST
	@Path("/insert/courseenrolled")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoCourseEnrolled(Map<String, Object> payload) {
		CourseEnrolled response = service.insertIntoCourseEnrolled(payload);
		GenericEntity<CourseEnrolled> responseEntity = null;
		
		try {
			if (response != null) {
				responseEntity = new GenericEntity<>(response) {};
				return Response.ok(responseEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/get/postadvertisementresponse/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllPostAdvertisementResponsesByFarmerId(@PathParam(value = "farmerId") Integer farmerId) {
		List<PostAdvertisementResponse> postAdvertisementResponses = service.selectAllPostAdvertisementResponsesByFarmerId(farmerId);
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
	
	@GET
	@Path("/get/croppayment/{farmerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllCropPaymentByFarmer(@PathParam(value = "farmerId") Integer farmerId) {
		List<CropPayment> cropPayments = service.selectAllCropPaymentByFarmer(farmerId);
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
	
	@POST
	@Path("/insert/croppayment")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoSellCropDetailsAndCropOrdersAndPayment(Map<String, Object> payload) {
		CropPayment cropPayment = service.insertIntoSellCropDetailsAndCropOrdersAndPayment(payload);
		GenericEntity<CropPayment> cropPaymentEntity = null;
		
		try {
			if (cropPayment != null) {
				cropPaymentEntity = new GenericEntity<>(cropPayment) {};
				return Response.ok(cropPaymentEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("/update/croporders")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateCropOrderStatus(Map<String, Object> payload) {
		CropPayment cropPayment = service.updateCropOrderStatus(payload);
		GenericEntity<CropPayment> cropPaymentEntity = null;
		
		try {
			if (cropPayment != null) {
				cropPaymentEntity = new GenericEntity<>(cropPayment) {};
				return Response.ok(cropPaymentEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
