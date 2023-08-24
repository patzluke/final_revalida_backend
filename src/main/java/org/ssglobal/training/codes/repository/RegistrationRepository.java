package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.Users;

@Repository
public class RegistrationRepository {
	
	@Autowired
    private SessionFactory sf;

	public Object registerUser(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Users user = new Users();
			user.setUsername(payload.get("userName").toString());
			user.setPassword(payload.get("password").toString());
			user.setEmail(payload.get("email").toString());
			user.setContactNo(payload.get("contactNo").toString());
			user.setSocials((String[]) payload.get("socials"));
			user.setFirstName(payload.get("firstName").toString());
			user.setMiddleName(payload.get("middleName").toString());
			user.setLastName(payload.get("lastName").toString());
			user.setUserType(payload.get("userType").toString());
			user.setBirthDate(LocalDate.parse(payload.get("birthDate").toString()));
			user.setAddress(payload.get("address").toString());
			user.setCivilStatus(payload.get("civilStatus").toString());
			user.setGender(payload.get("gender").toString());
			user.setNationality(payload.get("nationality").toString());
			user.setActiveDeactive(true);
			user.setActiveDeactive(true);
			user.setDateCreated(LocalDateTime.now());
			
			sess.persist(user);
			sess.flush();
			
			if (user.getUserType().equalsIgnoreCase("Farmer")) {
				Farmer farmer = new Farmer();
				farmer.setUser(user);
				sess.persist(farmer);
				tx.commit();
				return farmer;
			} else if (user.getUserType().equalsIgnoreCase("Supplier")) {
				Supplier supplier = new Supplier();
				supplier.setUser(user);
				sess.persist(supplier);
				tx.commit();
				return supplier;
			}
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return null;
	}
}
