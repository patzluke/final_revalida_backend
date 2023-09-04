package org.ssglobal.training.codes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.UserNotifications;
import org.ssglobal.training.codes.repository.UserNotificationsRepository;
import org.ssglobal.training.codes.service.UserNotificationsService;

@Service
public class UserNotificationsServiceImpl implements UserNotificationsService {

	@Autowired
	private UserNotificationsRepository repository;
	
	@Override
	public List<UserNotifications> selectAllNotificationsOfUserByUserId(Integer userId) {
		return repository.selectAllNotificationsOfUserByUserId(userId);
	}
	
	@Override
	public List<UserNotifications> updateAllNotificationsToReadOfUserByUserId(Integer userId) {
		return repository.updateAllNotificationsToReadOfUserByUserId(userId);
	}
}
