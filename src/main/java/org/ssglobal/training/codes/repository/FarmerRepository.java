package org.ssglobal.training.codes.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.Users;
import org.ssglobal.training.codes.models.PostAdvertisementResponse.PostAdvertisementResponseBuilder;

@Repository
public class FarmerRepository {
	
	@Autowired
    private SessionFactory sf;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Optional<Farmer> findOneByUserId(Integer userId) {
		// Named Parameter
		String sql = "SELECT * FROM farmer WHERE user_id = :user_id";

		try (Session sess = sf.openSession()) {
			Query<Farmer> query = sess.createNativeQuery(sql, Farmer.class);
			query.setParameter("user_id", userId);
			Farmer record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Farmer updateFarmerInfo(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Users user = sess.get(Users.class, Integer.valueOf(payload.get("userId").toString()));
			try {
				user.setPassword(encoder.encode(payload.get("password").toString()));
			} catch (NullPointerException e) {	}
			try {
				user.setSocials(((List<String>) payload.get("socials")).toArray(new String[] {}));
				user.setEmail(payload.get("email").toString());
			} catch (NullPointerException e) {	}
			try {
				user.setImage(payload.get("image").toString());
			} catch (NullPointerException e) {	}
			sess.merge(user);
			tx.commit();
			return findOneByUserId(Integer.valueOf(payload.get("userId").toString())).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Farmer> findOneByFarmerId(Integer farmerId) {
		// Named Parameter
		String sql = "SELECT * FROM farmer WHERE farmer_id = :farmer_id";

		try (Session sess = sf.openSession()) {
			Query<Farmer> query = sess.createNativeQuery(sql, Farmer.class);
			query.setParameter("farmer_id", farmerId);
			Farmer record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Optional<PostAdvertisement> findOneByAdvertisementById(Integer postId) {
		// Named Parameter
		String sql = "SELECT * FROM post_advertisement WHERE post_id = :post_id";

		try (Session sess = sf.openSession()) {
			Query<PostAdvertisement> query = sess.createNativeQuery(sql, PostAdvertisement.class);
			query.setParameter("post_id", postId);
			PostAdvertisement record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//Farmer Complaint
	public List<FarmerComplaint> selectFarmerComplaints(Integer farmerId) {
		List<FarmerComplaint> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "select * from farmer_complaint where farmer_id = :farmer_id order by farmer_complaint_id desc";

		try (Session sess = sf.openSession()) {
			Query<FarmerComplaint> query = sess.createNativeQuery(sql, FarmerComplaint.class);
			query.setParameter("farmer_id", farmerId);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	public FarmerComplaint insertIntoFarmerComplaint(Map<String, Object> payload) {		
		FarmerComplaint complaint = new FarmerComplaint();
		complaint.setActiveDeactive(true);
		complaint.setComplaintTitle(payload.get("complaintTitle").toString());
		complaint.setComplaintMessage(payload.get("complaintMessage").toString());
		complaint.setFarmer(findOneByFarmerId(Integer.valueOf(payload.get("farmerId").toString())).orElse(null));
		complaint.setDateSubmitted(LocalDateTime.now());
		
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			
			sess.persist(complaint);
			tx.commit();
			return complaint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public FarmerComplaint updateIntoFarmerComplaint(FarmerComplaint farmerComplaint) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
		
			FarmerComplaint updatedComplaint = sess.get(FarmerComplaint.class, farmerComplaint.getFarmerComplaintId());
			updatedComplaint.setComplaintTitle(farmerComplaint.getComplaintTitle());
			updatedComplaint.setComplaintMessage(farmerComplaint.getComplaintMessage());
			sess.merge(updatedComplaint);
			tx.commit();
			return updatedComplaint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public FarmerComplaint softDeleteFarmerComplaint(Integer farmingTipId) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			FarmerComplaint updatedComplaint = sess.get(FarmerComplaint.class, farmingTipId);
			updatedComplaint.setActiveDeactive(false);
			sess.merge(updatedComplaint);
			tx.commit();
			return updatedComplaint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Post Advertisement
	public List<PostAdvertisement> selectAllPostAdvertisements() {
		List<PostAdvertisement> records = new ArrayList<>();
		String sql = "select * from post_advertisement order by post_id desc";

		try (Session sess = sf.openSession()) {
			Query<PostAdvertisement> query = sess.createNativeQuery(sql, PostAdvertisement.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	//Post Advertisement Responses
	public PostAdvertisementResponse insertIntoPostAdvertisementResponse(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			PostAdvertisementResponseBuilder response = PostAdvertisementResponse.builder();
			response.price(Double.valueOf(payload.get("price").toString()));
			response.quantity(payload.get("quantity").toString());
			response.dateCreated(LocalDateTime.now());
			response.message(payload.get("message").toString());
			response.preferredPaymentMode(payload.get("preferredPaymentMode").toString());
			response.farmer(findOneByFarmerId(Integer.valueOf(payload.get("farmerId").toString())).orElse(null));
			response.postAdvertisement(findOneByAdvertisementById(Integer.valueOf(payload.get("postId").toString())).orElse(null));
			tx = sess.beginTransaction();
		
			sess.persist(response.build());
			tx.commit();
			return response.build();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
