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
import org.ssglobal.training.codes.models.CropOrder;
import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.UserNotifications;
import org.ssglobal.training.codes.models.Users;

@Repository
public class SupplierRepository {
	
	@Autowired
    private SessionFactory sf;
	
	@Autowired
	private PasswordEncoder encoder;
	
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
	
	public Optional<Users> findUserByUserId(Integer userId) {
		// Named Parameter
		String sql = "SELECT * FROM users WHERE user_id = :user_id";
		try (Session sess = sf.openSession()) {
			Query<Users> query = sess.createNativeQuery(sql, Users.class);
			query.setParameter("user_id", userId);
			Users record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Supplier updateSupplierInfo(Map<String, Object> payload) {
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
			try {
				user.setValidIdPicture(payload.get("validIdPicture").toString());
				user.setValidIdNumber(payload.get("validIdNumber").toString());
				user.setValidIdType(payload.get("validIdType").toString());
			} catch (NullPointerException e) {	}
			sess.merge(user);
			tx.commit();
			return findOneByUserId(Integer.valueOf(payload.get("userId").toString())).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
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
		advertisement.setMeasurement(payload.get("measurement").toString());
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
			advertisement.setMeasurement(payload.get("measurement").toString());
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
	
	// Post Advertisement Responses
	@SuppressWarnings("unchecked")
	public UserNotifications insertIntoUserNotifications(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();

			UserNotifications notification = new UserNotifications();
			String userId = ((Map<String, Object>) ((Map<String, Object>) payload.get("farmer")).get("user")).get("userId").toString();
			notification.setUser(findUserByUserId(Integer.valueOf(userId)).orElse(null));
			notification.setNotificationTitle(payload.get("notificationTitle").toString());
			notification.setNotificationMessage(payload.get("notificationMessage").toString());
			notification.setIsRead(false);
			notification.setDateCreated(LocalDateTime.now());
		
			sess.persist(notification);
			tx.commit();
			return notification;
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Crop Payment
	public List<CropPayment> selectAllCropPaymentBySupplier(Integer supplierId) {
		List<CropPayment> records = new ArrayList<>();
		String sql = "select cp.* from crop_payment cp \r\n"
				+ "inner join crop_orders co on cp.order_id_ref = co.order_id_ref\r\n"
				+ "inner join sell_crop_details scd on scd.sell_id = co.sell_id\r\n"
				+ "where co.supplier_id = :supplier_id order by pay_date";
		
		try (Session sess = sf.openSession()) {
			Query<CropPayment> query = sess.createNativeQuery(sql, CropPayment.class);
			query.setParameter("supplier_id", supplierId);
			records = query.getResultList();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	@SuppressWarnings("unchecked")
	public CropPayment updateCropPaymentStatus(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			
			PostAdvertisementResponse response = sess.get(PostAdvertisementResponse.class, Integer.valueOf(payload.get("postResponseId").toString()));
			response.setIsFinalOfferAccepted(true);
			sess.merge(response);
			
			String orderIdRef = ((Map<String, Object>) ((Map<String, Object>) payload.get("cropOrder"))).get("orderIdRef").toString();
			String address = ((Map<String, Object>) ((Map<String, Object>) payload.get("cropOrder"))).get("address").toString();
			CropOrder order = sess.get(CropOrder.class, orderIdRef);
			order.setAddress(address);
			sess.merge(order);
			
			CropPayment cropPayment = sess.get(CropPayment.class, payload.get("paymentId").toString());
			Users user = findOneByUserId(Integer.valueOf(payload.get("userId").toString())).orElse(null).getUser();
			cropPayment.setTranscationReferenceNumber(payload.get("transactionNumber").toString());
			cropPayment.setPayDate(LocalDateTime.now());
			cropPayment.setPaidBy("%s %s %s".formatted(user.getFirstName(), user.getMiddleName(), user.getLastName()));
			cropPayment.setProofOfPaymentImage(payload.get("proofOfPaymentImage").toString());
			sess.merge(cropPayment);
			
			tx.commit();
			return cropPayment;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
