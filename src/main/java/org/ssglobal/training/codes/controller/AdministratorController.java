package org.ssglobal.training.codes.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.Course;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.FarmingTip;
import org.ssglobal.training.codes.models.Supplier;
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
	@Path("/get/admin/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findOneByUserId(@PathParam(value = "userId") Integer userId) {
		Optional<Administrator> administrator = service.findOneByUserId(userId);
		GenericEntity<Administrator> administratorEntity = null;
		try {
			if (!administrator.isEmpty()) {
				administratorEntity = new GenericEntity<>(administrator.orElse(null)) {
				};
				return Response.ok(administratorEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("/update/admin")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateIntoFarmingTip(Map<String, Object> payload) {
		Administrator administrator = service.updateAdminInfo(payload);
		GenericEntity<Administrator> administratorEntity = null;
		
		try {
			if (administrator != null) {
				administratorEntity = new GenericEntity<>(administrator) {};
				return Response.ok(administratorEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	// Farmers
	@GET
	@Path("/get/farmers")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllFarmers() {
		List<Farmer> farmers = service.selectAllFarmers();
		GenericEntity<List<Farmer>> farmersEntity = null;
		try {
			if (!farmers.isEmpty()) {
				farmersEntity = new GenericEntity<>(farmers) {
				};
				return Response.ok(farmersEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	// Suppliers
	@GET
	@Path("/get/suppliers")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllSuppliers() {
		List<Supplier> suppliers = service.selectAllSuppliers();
		GenericEntity<List<Supplier>> suppliersEntity = null;
		try {
			if (!suppliers.isEmpty()) {
				suppliersEntity = new GenericEntity<>(suppliers) {
				};
				return Response.ok(suppliersEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	//FarmingTips
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
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@POST
	@Path("/insert/farmingtips")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoFarmingTip(FarmingTip payload) {
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
	
	//Farmer Complaints
	@GET
	@Path("/get/farmercomplaints")
    @Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllFarmerComplaints() {
		List<FarmerComplaint> farmerComplaints = service.selectAllFarmerComplaints();
		GenericEntity<List<FarmerComplaint>> farmingComplaintEntity = null;
		try {
			if (!farmerComplaints.isEmpty()) {
				farmingComplaintEntity = new GenericEntity<>(farmerComplaints) {};
				return Response.ok(farmingComplaintEntity).build();
			}
		} catch (Exception e) {
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
		updatedComplaint.setAdminReplyMessage(payload.get("adminReplyMessage").toString());
		updatedComplaint.setReadDate(LocalDateTime.now());
		updatedComplaint.setIsRead(Boolean.valueOf(payload.get("isRead").toString()));
		updatedComplaint.setIsResolved(Boolean.valueOf(payload.get("isResolved").toString()));
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
	
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/verify/account")
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response validateUserAccount(Map<String, Object> payload) {
		Object user = service.validateUserAccount((Map<String, Object>) payload.get("user"));
		GenericEntity<Object> userEntity = null;
		
		try {
			if (user != null) {
				userEntity = new GenericEntity<>(user) {};
				return Response.ok(userEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	// FarmingTips
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

	@POST
	@Path("/insert/course")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insertIntoCourses(Map<String, Object> payload) {
		Course course = service.insertIntoCourses(payload);
		GenericEntity<Course> courseEntity = null;

		try {
			if (course != null) {
				courseEntity = new GenericEntity<>(course) {
				};
				return Response.ok(courseEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@PUT
	@Path("/update/course")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateIntoCourses(Map<String, Object> payload) {
		Course course = service.updateIntoCourses(payload);
		GenericEntity<Course> courseEntity = null;

		try {
			if (course != null) {
				courseEntity = new GenericEntity<>(course) {
				};
				return Response.ok(courseEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("/delete/course/{courseId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response deleteCourse(@PathParam(value = "courseId") Integer courseId) {
		Course course = service.deleteCourse(courseId);
		GenericEntity<Course> courseEntity = null;

		try {
			if (course != null) {
				courseEntity = new GenericEntity<>(course) {
				};
				return Response.ok(courseEntity).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
