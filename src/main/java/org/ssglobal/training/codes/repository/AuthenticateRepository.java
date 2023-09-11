package org.ssglobal.training.codes.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.EmailDetails;
import org.ssglobal.training.codes.models.Otp;
import org.ssglobal.training.codes.models.Users;
import org.ssglobal.training.codes.service.EmailService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRepository {
	
	@Autowired
    private SessionFactory sf;
	
	@Autowired 
	private EmailService emailService;
	
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
	
	public Optional<Users> findOneByUsername(String username) {
		// Named Parameter
		String sql = "SELECT * FROM users WHERE username = :username";

		try (Session sess = sf.openSession()) {
			Query<Users> query = sess.createNativeQuery(sql, Users.class);
			query.setParameter("username", username);
			Users record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Users updatePassword(Users user) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();

			Users updatedUser = sess.get(Users.class, user.getUserId());
			updatedUser.setPassword(user.getPassword());
			updatedUser = sess.merge(updatedUser);
			tx.commit();
			return updatedUser;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Optional<Users> findUserByEmail(String email) {
		// Named Parameter
		String sql = "SELECT * FROM users WHERE email = :email";

		try (Session sess = sf.openSession()) {
			Query<Users> query = sess.createNativeQuery(sql, Users.class);
			query.setParameter("email", email);
			Users record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//Otp
	public Otp insertIntoOtp(Map<String, Object> payload) {		
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			String sixRandomNumber = RandomStringUtils.randomNumeric(6);
			Otp otp = new Otp();
			otp.setUser(findUserByEmail(payload.get("email").toString()).orElse(null));
			otp.setIssuedTime(LocalDateTime.now());
			otp.setExpiryTime(LocalDateTime.now().plusMinutes(5L));
	        otp.setOtpCode(sixRandomNumber);

			EmailDetails emailDetails = new EmailDetails();
			emailDetails.setRecipient(otp.getUser().getEmail());
			emailDetails.setSubject("User Verification");
			emailDetails.setMsgBody("""
					Hi %s,
					Here is your generated otp code.
					Remember! It is valid for only 5 minutes!
					The verification code is: %s
					
					Regards,
					AgriBridge
					""".formatted(otp.getUser().getFirstName(), otp.getOtpCode()));
			emailService.sendSimpleMail(emailDetails);
			sess.persist(otp);
			tx.commit();
			return otp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Otp
	public Otp updateIntoOtp(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			String sixRandomNumber = RandomStringUtils.randomNumeric(6);
			Otp otp = sess.get(Otp.class, Integer.valueOf(payload.get("otpId").toString()));
			otp.setOtpCode(sixRandomNumber);
			otp.setIssuedTime(LocalDateTime.now());
			otp.setExpiryTime(LocalDateTime.now().plusMinutes(5L));
			
			EmailDetails emailDetails = new EmailDetails();
			emailDetails.setRecipient(otp.getUser().getEmail());
			emailDetails.setSubject("User Verification");
			emailDetails.setMsgBody("""
					Hi %s,
					Here is your newly generated otp code.
					Remember! It is valid for only 5 minutes!
					The verification code is: %s

					Regards,
					AgriBridge
					""".formatted(otp.getUser().getFirstName(), otp.getOtpCode()));
			emailService.sendSimpleMail(emailDetails);
			sess.persist(otp);
			tx.commit();
			return otp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String validateOtp(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Otp otp = sess.get(Otp.class, Integer.valueOf(payload.get("otpId").toString()));
			if (otp.getOtpCode().equals(payload.get("otpCode"))) {
				return "matched";
			}
			
			otp.setTries(otp.getTries() + 1);
			sess.persist(otp);
			if (otp.getTries() > 3) {
				sess.remove(otp);
				return "exceeded number of tries";
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Wrong Otp";
	}
	
	
}
