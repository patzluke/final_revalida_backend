package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ssglobal.training.codes.models.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
	
	@Query(value = "SELECT * FROM administrator WHERE user_id = :user_id", nativeQuery = true)
	Optional<Administrator> findOneByUserId(@Param(value = "user_id") Integer userId);
}
