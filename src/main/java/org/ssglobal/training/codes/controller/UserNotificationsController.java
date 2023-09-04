package org.ssglobal.training.codes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssglobal.training.codes.models.UserNotifications;
import org.ssglobal.training.codes.service.UserNotificationsService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usernotifications")
public class UserNotificationsController {
	
	@Autowired
	private UserNotificationsService service;

	
	@GET
	@Path("/get/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response selectAllNotificationsOfUserByUserId(@PathParam(value = "userId") Integer userId) {
		List<UserNotifications> userNotifications = service.selectAllNotificationsOfUserByUserId(userId);
		GenericEntity<List<UserNotifications>> userNotificationsEntity = null;
		try {
			if (!userNotifications.isEmpty()) {
				userNotificationsEntity = new GenericEntity<>(userNotifications) {
				};
				return Response.ok(userNotificationsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/update/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateAllNotificationsToReadOfUserByUserId(@PathParam(value = "userId") Integer userId) {
		List<UserNotifications> userNotifications = service.updateAllNotificationsToReadOfUserByUserId(userId);
		GenericEntity<List<UserNotifications>> userNotificationsEntity = null;
		try {
			if (!userNotifications.isEmpty()) {
				userNotificationsEntity = new GenericEntity<>(userNotifications) {
				};
				return Response.ok(userNotificationsEntity).build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
