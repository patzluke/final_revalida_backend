package org.ssglobal.training.codes.service.impl;

import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.RegistrationRepository;
import org.ssglobal.training.codes.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationRepository repository;
	
	@Override
	public Object registerUser(Map<String, Object> payload) throws ConstraintViolationException, Exception {
		return repository.registerUser(payload);
	}
}
