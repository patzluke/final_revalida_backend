package org.ssglobal.training.codes.service;

import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;

public interface RegistrationService {

	Object registerUser(Map<String, Object> payload) throws ConstraintViolationException, Exception;
}
