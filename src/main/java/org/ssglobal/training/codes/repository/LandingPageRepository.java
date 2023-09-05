package org.ssglobal.training.codes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.FarmingTip;
import org.ssglobal.training.codes.models.PostAdvertisement;

@Repository
public class LandingPageRepository {

	@Autowired
	private SessionFactory sf;

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

	// Crop Specialization
	public List<CropSpecialization> selectAllCropSpecialization() {
		List<CropSpecialization> records = new ArrayList<>();
		String sql = "select * from crop_specialization order by crop_specialization_id";

		try (Session sess = sf.openSession()) {
			Query<CropSpecialization> query = sess.createNativeQuery(sql, CropSpecialization.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	// Farming tips
	public List<FarmingTip> selectAllFarmingTips() {
		List<FarmingTip> records = new ArrayList<>();
		String sql = "select * from farming_tip order by farming_tip_id";

		try (Session sess = sf.openSession()) {
			Query<FarmingTip> query = sess.createNativeQuery(sql, FarmingTip.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
		
	}
}
