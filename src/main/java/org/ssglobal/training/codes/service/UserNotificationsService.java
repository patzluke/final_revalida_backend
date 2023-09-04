package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.models.UserNotifications;

public interface UserNotificationsService {
	
	List<UserNotifications> selectAllNotificationsOfUserByUserId(Integer userId);
	List<UserNotifications> updateAllNotificationsToReadOfUserByUserId(Integer userId);
}
