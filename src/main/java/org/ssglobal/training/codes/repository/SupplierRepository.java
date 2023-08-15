package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.Users;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	
	@Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
	Optional<Users> findOneByUsername(@Param(value = "username") String username);
}
