package org.ssglobal.training.codes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.FarmingTip;

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
	
	// Farming Tips
	public List<FarmingTip> selectAllFarmingTip() {
		List<FarmingTip> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "select * from farming_tip";

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
}
