package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ssglobal.training.codes.models.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Integer> {
	
	@Query(value = "SELECT * FROM farmer WHERE user_id = :user_id", nativeQuery = true)
	Optional<Farmer> findOneByUserId(@Param(value = "user_id") Integer userId);
}
