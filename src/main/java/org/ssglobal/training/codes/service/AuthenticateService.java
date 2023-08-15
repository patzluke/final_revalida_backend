package org.ssglobal.training.codes.service;

import java.util.Map;

import org.ssglobal.training.codes.models.Users;

public interface AuthenticateService {

	Map<String, Object> searchUserByUsernameAndPassword(String username, String password);
	boolean createToken(Integer employeeId, String token);
	boolean updateUserToken(Integer employeeId, String token);
	boolean deleteUserToken(Integer userId);
	boolean isUserTokenIdExists(Integer userId);
	boolean isUserTokenExists(Integer userId, String token);
	String generateToken(Integer userId, Integer userNo, String username, String userType, Boolean isActive);
	
	Users changePassword(String password, String username);
}
