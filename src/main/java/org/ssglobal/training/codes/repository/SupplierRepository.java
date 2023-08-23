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
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.Supplier;

@Repository
public class SupplierRepository {
	
	@Autowired(required = false)
    private SessionFactory sf;
	
	public Optional<Supplier> findOneByUserId(Integer userId) {
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
	
	public Optional<Supplier> findOneBySupplierId(Integer supplierId) {
		// Named Parameter
		String sql = "SELECT * FROM supplier WHERE supplier_id = :supplier_id";
		try (Session sess = sf.openSession()) {
			Query<Supplier> query = sess.createNativeQuery(sql, Supplier.class);
			query.setParameter("supplier_id", supplierId);
			Supplier record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Optional<CropSpecialization> findOneCropSpecializationById(Integer cropSpecializationId) {
		// Named Parameter
		String sql = "SELECT * FROM crop_specialization WHERE crop_specialization_id = :crop_specialization_id";
		try (Session sess = sf.openSession()) {
			Query<CropSpecialization> query = sess.createNativeQuery(sql, CropSpecialization.class);
			query.setParameter("crop_specialization_id", cropSpecializationId);
			CropSpecialization record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// Post Advertisement
	public List<PostAdvertisement> selectPostAdvertisementBySupplierId(Integer supplierId) {
		List<PostAdvertisement> records = new ArrayList<>();
		String sql = "select * from post_advertisement where supplier_id = :supplier_id order by post_id desc";

		try (Session sess = sf.openSession()) {
			Query<PostAdvertisement> query = sess.createNativeQuery(sql, PostAdvertisement.class);
			query.setParameter("supplier_id", supplierId);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	public PostAdvertisement insertIntoPostAdvertisement(Map<String, Object> payload) {		
		PostAdvertisement advertisement = new PostAdvertisement();
		advertisement.setSupplier(findOneBySupplierId(Integer.valueOf(payload.get("supplierId").toString())).orElse(null));
		advertisement.setCropSpecialization(findOneCropSpecializationById(Integer.valueOf(payload.get("cropSpecializationId").toString())).orElse(null));
		advertisement.setCropName(payload.get("cropName").toString());
		advertisement.setDescription(payload.get("description").toString());
		advertisement.setCropImage(payload.get("cropImage").toString());
		advertisement.setQuantity(payload.get("quantity").toString());
		advertisement.setPrice(Double.valueOf(payload.get("price").toString()));
		advertisement.setDatePosted(LocalDateTime.now());
		advertisement.setActiveDeactive(true);
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			
			sess.persist(advertisement);
			tx.commit();
			return advertisement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PostAdvertisement updateIntoPostAdvertisement(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
		
			PostAdvertisement advertisement = sess.get(PostAdvertisement.class, Integer.valueOf(payload.get("postId").toString()));
			advertisement.setCropSpecialization(findOneCropSpecializationById(Integer.valueOf(payload.get("cropSpecializationId").toString())).orElse(null));
			advertisement.setCropName(payload.get("cropName").toString());
			advertisement.setDescription(payload.get("description").toString());
			advertisement.setCropImage(payload.get("cropImage").toString());
			advertisement.setQuantity(payload.get("quantity").toString());
			advertisement.setPrice(Double.valueOf(payload.get("price").toString()));
			advertisement.setDateModified(LocalDateTime.now());
			sess.merge(advertisement);
			tx.commit();
			return advertisement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PostAdvertisement softDeletePostAdvertisement(Integer postId) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			PostAdvertisement advertisement = sess.get(PostAdvertisement.class, postId);
			advertisement.setActiveDeactive(false);
			sess.merge(advertisement);
			tx.commit();
			return advertisement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Crop Specialization
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
	
	// Post Advertisement Respones
	public List<PostAdvertisementResponse> selectAllPostAdvertisementResponsesByPostId(Integer postId) {
		List<PostAdvertisementResponse> records = new ArrayList<>();
		String sql = "select * from post_advertisement_responses where post_id = :post_id order by date_created";

		try (Session sess = sf.openSession()) {
			Query<PostAdvertisementResponse> query = sess.createNativeQuery(sql, PostAdvertisementResponse.class);
			query.setParameter("post_id", postId);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	public PostAdvertisementResponse updatePostAdvertisementResponsesIsAcceptedStatus(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
		
			PostAdvertisementResponse advertisementResponse = sess.get(PostAdvertisementResponse.class, Integer.valueOf(payload.get("postResponseId").toString()));
			advertisementResponse.setIsAccepted(Boolean.valueOf(payload.get("isAccepted").toString()));
			sess.merge(advertisementResponse);
			tx.commit();
			return advertisementResponse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
