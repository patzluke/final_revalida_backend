package org.ssglobal.training.codes.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.UserToken;
import org.ssglobal.training.codes.models.UserToken.UserTokenBuilder;
import org.ssglobal.training.codes.models.Users;
import org.ssglobal.training.codes.repository.AdministratorRepository;
import org.ssglobal.training.codes.repository.AuthenticateRepository;
import org.ssglobal.training.codes.repository.FarmerRepository;
import org.ssglobal.training.codes.repository.SupplierRepository;
import org.ssglobal.training.codes.repository.UserTokenRepository;
import org.ssglobal.training.codes.service.AuthenticateService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
	
	@Autowired
	private AuthenticateRepository authenticateRepository;
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Autowired
	private FarmerRepository farmerRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private UserTokenRepository userTokenRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Map<String, Object> searchUserByUsernameAndPassword(String username, String password) {
		Map<String, Object> user = new HashMap<>(); 
		Optional<Users> userOptional = authenticateRepository.findOneByUsername(username);		
		if (userOptional.isPresent() && encoder.matches(password, userOptional.get().getPassword())) {
			user.put("userId", userOptional.get().getUserId());
			user.put("username", userOptional.get().getUsername());
			user.put("userType", userOptional.get().getUserType());
			user.put("activeStatus", userOptional.get().getActiveStatus());
			
			if (userOptional.get().getUserType().equalsIgnoreCase("Administrator")) {
				Administrator administrator = administratorRepository.findOneByUserId(userOptional.get().getUserId()).orElse(null);
				user.put("userNo", administrator.getAdministratorId());
			}
			if (userOptional.get().getUserType().equalsIgnoreCase("Farmer")) {
				Farmer farmer = farmerRepository.findOneByUserId(userOptional.get().getUserId()).orElse(null);
				user.put("userNo", farmer.getFarmerId());
			}
			if (userOptional.get().getUserType().equalsIgnoreCase("Supplier")) {
				Supplier supplier = supplierRepository.findOneByUserId(userOptional.get().getUserId()).orElse(null);
				user.put("userNo", supplier.getSupplierId());
			}
			return user;
		}
		return null;
	}

	@Override
	public boolean createToken(Integer userId, String token) {
		UserTokenBuilder userToken = UserToken.builder();
		userToken.userId(userId);
		userToken.token(token);
		if (userTokenRepository.createToken(userToken.build())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUserToken(Integer userId) {
		Optional<UserToken> userTokenOptional = userTokenRepository.findOneByUserId(userId);
		if (userTokenOptional.isPresent()) {
			userTokenRepository.deleteById(userTokenOptional.get().getUserId());
			return true;
		}
		return false;
	}

	@Override
	public boolean isUserTokenIdExists(Integer userId) {
		if (userTokenRepository.findOneByUserId(userId).isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isUserTokenExists(Integer userId, String token) {
		Optional<UserToken> userTokenOptional = userTokenRepository.findOneByUserIdAndToken(userId, token);
		if (userTokenOptional.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String generateToken(Integer userId, Integer userNo, String username, String userType, Boolean isActive) {
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder()
							  .claim("userId", userId)
							  .claim("userNo", userNo)
							  .claim("userType", userType)
							  .claim("username", username)
							  .claim("isActive", isActive)
							  .setIssuedAt(new Date())
							  .setExpiration(Date.from(LocalDateTime.now().plusMinutes(90L).atZone(ZoneId.systemDefault()).toInstant()))
							  .signWith(key, SignatureAlgorithm.HS256)
							  .compact();
		if (isUserTokenIdExists(userId)) {
			deleteUserToken(userId);
		}
		createToken(userId, jwtToken);
		return jwtToken;
	}
	
	
	@Override
	public Users changePassword(String password, String username) {
		Users users  = authenticateRepository.findOneByUsername(username).orElse(null);
		users.setPassword(encoder.encode(password));
		users = authenticateRepository.updatePassword(users);
		return users;
	}
}
