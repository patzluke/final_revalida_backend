package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ssglobal.training.codes.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	
	@Query(value = "SELECT * FROM supplier WHERE user_id = :user_id", nativeQuery = true)
	Optional<Supplier> findOneByUserId(@Param(value = "user_id") Integer userId);
}
