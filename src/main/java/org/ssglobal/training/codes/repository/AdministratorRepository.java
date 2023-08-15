package org.ssglobal.training.codes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ssglobal.training.codes.models.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
	
}
