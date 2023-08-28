package org.ssglobal.training.codes.repository;

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
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.FarmingTip;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.UserApplicants;
import org.ssglobal.training.codes.models.Users;

@Repository
public class AdministratorRepository {
	
	@Autowired
    private SessionFactory sf;
	
	public List<Administrator> findAllAdministrators() {
		List<Administrator> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "SELECT * FROM administrator";

		try (Session sess = sf.openSession()) {
			Query<Administrator> query = sess.createNativeQuery(sql, Administrator.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	 
	public Optional<Administrator> findOneByUserId(Integer userId) {
		// Named Parameter
		String sql = "SELECT * FROM administrator WHERE user_id = :user_id";

		try (Session sess = sf.openSession()) {
			Query<Administrator> query = sess.createNativeQuery(sql, Administrator.class);
			query.setParameter("user_id", userId);
			Administrator record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Optional<Supplier> findSupplierByUserId(Integer userId) {
		// Named Parameter
		String sql = "SELECT * FROM supplier WHERE user_id = :user_id";
		try (Session sess = sf.openSession()) {
			Query<Supplier> query = sess.createNativeQuery(sql, Supplier.class);
			query.setParameter("user_id", userId);
			Supplier record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Optional<Farmer> findFarmerByUserId(Integer userId) {
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
	
	//User Applicants
	public List<UserApplicants> selectAllUserApplicants() {
		List<UserApplicants> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "SELECT * FROM user_applicants order by applicant_id";

		try (Session sess = sf.openSession()) {
			Query<UserApplicants> query = sess.createNativeQuery(sql, UserApplicants.class);
			records = query.getResultList();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	public Object validateUserAccount(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Users updatedUser = sess.get(Users.class, Integer.valueOf(payload.get("userId").toString()));
			updatedUser.setValidIdPicture(payload.get("validIdPicture").toString());
			updatedUser.setIsValidated(Boolean.valueOf(payload.get("isValidated").toString()));
			sess.merge(updatedUser);
			tx.commit();
			if (updatedUser.getUserType().equalsIgnoreCase("Farmer")) {
				return findFarmerByUserId(updatedUser.getUserId()).get();
			}
			if (updatedUser.getUserType().equalsIgnoreCase("Supplier")) {
				return findSupplierByUserId(updatedUser.getUserId()).get();
			}
			return updatedUser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Farmer list
	public List<Farmer> selectAllFarmers() {
		List<Farmer> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "SELECT * FROM farmer order by farmer_id";

		try (Session sess = sf.openSession()) {
			Query<Farmer> query = sess.createNativeQuery(sql, Farmer.class);
			records = query.getResultList();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	// Farmer list
	public List<Supplier> selectAllSuppliers() {
		List<Supplier> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "SELECT * FROM supplier order by supplier_id";

		try (Session sess = sf.openSession()) {
			Query<Supplier> query = sess.createNativeQuery(sql, Supplier.class);
			records = query.getResultList();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	// Farming Tips
	public List<FarmingTip> selectAllFarmingTip() {
		List<FarmingTip> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "select * from farming_tip order by farming_tip_id desc";

		try (Session sess = sf.openSession()) {
			Query<FarmingTip> query = sess.createNativeQuery(sql, FarmingTip.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}

	public FarmingTip insertIntoFarmingTip(FarmingTip farmingTip) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			sess.persist(farmingTip);
			tx.commit();
			return farmingTip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public FarmingTip updateIntoFarmingTip(FarmingTip farmingTip) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			FarmingTip updatedTip = sess.get(FarmingTip.class, farmingTip.getFarmingTipId());
			updatedTip.setTipMessage(farmingTip.getTipMessage());
			updatedTip.setDateModified(farmingTip.getDateModified());
			sess.merge(updatedTip);
			tx.commit();
			return updatedTip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public FarmingTip deleteFarmingTip(Integer farmingTipId) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			FarmingTip tip = sess.get(FarmingTip.class, farmingTipId);
			sess.remove(tip);
			tx.commit();
			return tip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Farmer Complaints
	public List<FarmerComplaint> selectAllFarmerComplaints() {
		List<FarmerComplaint> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "select * from farmer_complaint where active_deactive = 't' order by farmer_complaint_id";

		try (Session sess = sf.openSession()) {
			Query<FarmerComplaint> query = sess.createNativeQuery(sql, FarmerComplaint.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}

	public FarmerComplaint updateIntoFarmerComplaint(FarmerComplaint farmerComplaint) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
		
			FarmerComplaint updatedComplaint = sess.get(FarmerComplaint.class, farmerComplaint.getFarmerComplaintId());
			updatedComplaint.setAdminReplyMessage(farmerComplaint.getAdminReplyMessage());
			if (updatedComplaint.getReadDate() == null) {
				updatedComplaint.setReadDate(farmerComplaint.getReadDate());				
			}
			
			updatedComplaint.setIsRead(farmerComplaint.getIsRead());
			sess.merge(updatedComplaint);
			tx.commit();
			return updatedComplaint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
