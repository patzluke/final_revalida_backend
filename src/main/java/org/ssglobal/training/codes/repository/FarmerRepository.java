package org.ssglobal.training.codes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;

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
}
