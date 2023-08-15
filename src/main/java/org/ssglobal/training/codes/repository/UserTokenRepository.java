package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ssglobal.training.codes.models.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
	
	@Query(value = "SELECT * FROM user_tokens WHERE user_id = :user_id", nativeQuery = true)
	Optional<UserToken> findOneByUserId(@Param(value = "user_id") Integer userId);
	
	@Query(value = "SELECT * FROM user_tokens WHERE user_id = :user_id and token = :token", nativeQuery = true)
	Optional<UserToken> findOneByUserIdAndToken(@Param(value = "user_id") Integer userId, @Param(value = "token") String token);
}
