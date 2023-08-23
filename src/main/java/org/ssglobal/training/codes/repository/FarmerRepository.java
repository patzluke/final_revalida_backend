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
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.PostAdvertisement;

@Repository
public class FarmerRepository {
	
	@Autowired
    private SessionFactory sf;
	
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
}
