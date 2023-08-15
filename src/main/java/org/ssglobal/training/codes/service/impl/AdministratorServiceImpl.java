package org.ssglobal.training.codes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.repository.AdministratorRepository;
import org.ssglobal.training.codes.service.AdministratorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	private AdministratorRepository repository;
	
	@Override
	public List<Administrator> selectAllAdministrators() {
		return repository.findAll();
	}
}
