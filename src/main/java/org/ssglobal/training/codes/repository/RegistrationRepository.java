package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.Users;

@Repository
public class RegistrationRepository {
	
	@Autowired
    private SessionFactory sf;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Optional<List<Users>> findAllUsers() {
		// Named Parameter
		String sql = "SELECT * FROM users order by user_id";

		try (Session sess = sf.openSession()) {
			Query<Users> query = sess.createNativeQuery(sql, Users.class);
			List<Users> record = query.getResultList();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object registerUser(Map<String, Object> payload) throws ConstraintViolationException, Exception {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Users user = new Users();
			user.setUsername(payload.get("username").toString());
			user.setPassword(encoder.encode(payload.get("password").toString()));
			user.setEmail(payload.get("email").toString());
			user.setContactNo(payload.get("contactNo").toString());
			user.setSocials(((List<String>) payload.get("socials")).toArray(new String[] {}));
			user.setFirstName(payload.get("firstName").toString());
			user.setMiddleName(payload.get("middleName").toString());
			user.setLastName(payload.get("lastName").toString());
			user.setUserType(payload.get("userType").toString());
			user.setBirthDate(LocalDate.parse(payload.get("birthDate").toString(), DateTimeFormatter.ISO_DATE_TIME));
			user.setAddress(payload.get("address").toString());
			user.setGender(payload.get("gender").toString());
			user.setNationality(payload.get("nationality").toString());
			user.setCivilStatus(payload.get("civilStatus").toString());
			user.setDateCreated(LocalDateTime.now());
			user.setValidIdPicture(payload.get("validIdPicture").toString());
			user.setIsValidated(false);
			user.setActiveStatus(true);
			user.setActiveDeactive(true);
			
			sess.persist(user);
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
		} catch (ConstraintViolationException e) {
			if (e.getConstraintName().equals("users_username_key")) {
				throw new ConstraintViolationException("username already exists", e.getSQLException(), e.getConstraintName());
			}
			if (e.getConstraintName().equals("users_email_key")) {
				throw new ConstraintViolationException("email already exists", e.getSQLException(), e.getConstraintName());
			}
			if (e.getConstraintName().equals("users_contact_no_key")) {
				throw new ConstraintViolationException("mobile number already exists", e.getSQLException(), e.getConstraintName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
