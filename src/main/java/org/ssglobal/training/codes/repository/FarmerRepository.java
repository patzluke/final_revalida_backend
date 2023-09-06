package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Course;
import org.ssglobal.training.codes.models.CourseEnrolled;
import org.ssglobal.training.codes.models.CropOrder;
import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.SellCropDetail;
import org.ssglobal.training.codes.models.Supplier;
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
	
	public Optional<Supplier> findSupplierBySupplierId(Integer supplierId) {
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
			try {
				user.setValidIdPicture(payload.get("validIdPicture").toString());
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
	
	public Optional<PostAdvertisementResponse> findOneByAdvertisementResponseById(Integer postResponseId) {
		// Named Parameter
		String sql = "SELECT * FROM post_advertisement_responses WHERE post_response_id = :post_response_id";

		try (Session sess = sf.openSession()) {
			Query<PostAdvertisementResponse> query = sess.createNativeQuery(sql, PostAdvertisementResponse.class);
			query.setParameter("post_response_id", postResponseId);
			PostAdvertisementResponse record = query.getSingleResultOrNull();

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
	
	public double calculateTotalSales(Integer farmerId) {
	    double totalSales = 0.0; // Initialize totalSales
	    
	    String sql = "select sum(price) as total_price from sell_crop_details where farmer_id = :farmer_id";
	    try (Session session = sf.openSession()) {
	        Query<Double> query = session.createNativeQuery(sql, Double.class);
	        query.setParameter("farmer_id", farmerId);
	        List<Double> prices = query.getResultList();

	        if (prices != null && !prices.isEmpty()) {
	            for (Double price : prices) {
	                totalSales += price;
	            }
	        }

	    } catch (HibernateException e) {
	        // Handle Hibernate-specific exceptions
	        // Log the exception or perform error handling
	        e.printStackTrace();
	    } catch (Exception e) {
	        // Handle other exceptions
	        // Log the exception or perform error handling
	        e.printStackTrace();
	    }
	    
	    return totalSales;
	}

	
	public FarmerComplaint insertIntoFarmerComplaint(Map<String, Object> payload) {		
		FarmerComplaint complaint = new FarmerComplaint();
		complaint.setActiveDeactive(true);
		complaint.setComplaintTitle(payload.get("complaintTitle").toString());
		complaint.setComplaintType(payload.get("complaintType").toString());
		complaint.setComplaintMessage(payload.get("complaintMessage").toString());
		complaint.setFarmer(findOneByFarmerId(Integer.valueOf(payload.get("farmerId").toString())).orElse(null));
		complaint.setDateSubmitted(LocalDateTime.now());
		complaint.setImage(payload.get("complaintImage").toString());
		
		
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
			response.isAccepted(false);
			response.isFinalOfferSubmitted(false);
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
	
	// Course
	public List<Course> selectAllCourses() {
		List<Course> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "select * from course order by course_id";

		try (Session sess = sf.openSession()) {
			Query<Course> query = sess.createNativeQuery(sql, Course.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	public Optional<Course> findCourseById(Integer courseId) {
		// Named Parameter
		String sql = "SELECT * FROM course WHERE course_id = :course_id";

		try (Session sess = sf.openSession()) {
			Query<Course> query = sess.createNativeQuery(sql, Course.class);
			query.setParameter("course_id", courseId);
			Course record = query.getSingleResult();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// course enrolled
	public List<CourseEnrolled> selectAllCoursesEnrolledByFarmer(Integer farmerId) {
		List<CourseEnrolled> records = new ArrayList<>();
		String sql = "select * from course_enrolled where farmer_id = :farmer_id order by enrollment_id";

		try (Session sess = sf.openSession()) {
			Query<CourseEnrolled> query = sess.createNativeQuery(sql, CourseEnrolled.class);
			query.setParameter("farmer_id", farmerId);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}

	public CourseEnrolled insertIntoCourseEnrolled(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			CourseEnrolled courseEnrolled = new CourseEnrolled();
			courseEnrolled.setFarmer(findOneByFarmerId(Integer.valueOf(payload.get("farmerId").toString())).orElse(null));
			courseEnrolled.setCourse(findCourseById(Integer.valueOf(payload.get("courseId").toString())).orElse(null));
			courseEnrolled.setEnrollmentDate(LocalDate.now());
			courseEnrolled.setEndOfEnrollment(LocalDate.now().plusDays(Long.valueOf(payload.get("durationInDays").toString())));
			sess.persist(courseEnrolled);
			tx.commit();
			return courseEnrolled;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Post Advertisement Respones
	public List<PostAdvertisementResponse> selectAllPostAdvertisementResponsesByFarmerId(Integer farmerId) {
		List<PostAdvertisementResponse> records = new ArrayList<>();
		String sql = "select * from post_advertisement_responses where farmer_id = :farmer_id order by date_created";

		try (Session sess = sf.openSession()) {
			Query<PostAdvertisementResponse> query = sess.createNativeQuery(sql, PostAdvertisementResponse.class);
			query.setParameter("farmer_id", farmerId);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}
	
	// Crop Payment
	public List<CropPayment> selectAllCropPaymentByFarmer(Integer farmerId) {
		List<CropPayment> records = new ArrayList<>();
		String sql = "select cp.* from crop_payment cp \r\n"
				+ "inner join crop_orders co on cp.order_id_ref = co.order_id_ref\r\n"
				+ "inner join sell_crop_details scd on scd.sell_id = co.sell_id\r\n"
				+ "where scd.farmer_id = :farmer_id order by pay_date";
		
		try (Session sess = sf.openSession()) {
			Query<CropPayment> query = sess.createNativeQuery(sql, CropPayment.class);
			query.setParameter("farmer_id", farmerId);
			records = query.getResultList();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}

	public CropPayment insertIntoSellCropDetailsAndCropOrdersAndPayment(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			
			PostAdvertisementResponse response = sess.get(PostAdvertisementResponse.class, Integer.valueOf(payload.get("postResponseId").toString()));
			response.setIsFinalOfferSubmitted(true);
			sess.merge(response);
			
			SellCropDetail sellCropDetail = new SellCropDetail();
			sellCropDetail.setFarmer(findOneByFarmerId(Integer.valueOf(payload.get("farmerId").toString())).orElse(null));
			sellCropDetail.setPostAdvertisementResponse(findOneByAdvertisementResponseById(Integer.valueOf(payload.get("postResponseId").toString())).orElse(null));
			sellCropDetail.setCropName(payload.get("cropName").toString());
			sellCropDetail.setPrice(Double.valueOf(payload.get("price").toString()));
			sellCropDetail.setQuantity(payload.get("quantity").toString());
			sellCropDetail.setMobilenumBanknumber(payload.get("mobilenumBanknumber").toString());
			sellCropDetail.setPaymentMode(payload.get("paymentMode").toString());
			sess.persist(sellCropDetail);
			
			CropOrder cropOrder = new CropOrder();
			cropOrder.setSellCropDetail(sellCropDetail);
			cropOrder.setSupplier(findSupplierBySupplierId(Integer.valueOf(payload.get("supplierId").toString())).orElse(null));
			cropOrder.setAddress(payload.get("address").toString());
			cropOrder.setIsReceivedBySupplier(false);
			cropOrder.setIsPaymentReceivedByFarmer(false);
			cropOrder.setOrderStatus("To Deliver");
			sess.persist(cropOrder);
			
			CropPayment cropPayment = new CropPayment();
			cropPayment.setCropOrder(cropOrder);
			sess.persist(cropPayment);
			tx.commit();
			return cropPayment;
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
