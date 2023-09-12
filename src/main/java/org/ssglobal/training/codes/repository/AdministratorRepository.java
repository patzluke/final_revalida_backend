package org.ssglobal.training.codes.repository;

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
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.Course;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.FarmingTip;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.SupplierComplaint;
import org.ssglobal.training.codes.models.Users;

@Repository
public class AdministratorRepository {
	
	@Autowired
    private SessionFactory sf;
	
	@Autowired
	private PasswordEncoder encoder;
	
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

			return Optional.ofNullable(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Administrator updateAdminInfo(Map<String, Object> payload) {
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
	
	public Optional<Supplier> findSupplierByUserId(Integer userId) {
		// Named Parameter
		String sql = "SELECT * FROM supplier WHERE user_id = :user_id";
		try (Session sess = sf.openSession()) {
			Query<Supplier> query = sess.createNativeQuery(sql, Supplier.class);
			query.setParameter("user_id", userId);
			Supplier record = query.getSingleResultOrNull();

			return Optional.ofNullable(record);
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

			return Optional.ofNullable(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Object changeUserActiveStatus(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Integer userId = Integer.valueOf(payload.get("userId").toString());
			Users updatedUser = sess.get(Users.class, userId);
			updatedUser.setActiveStatus(!updatedUser.getActiveStatus());
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
	
	public Object validateUserAccount(Map<String, Object> payload) {
		
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Users updatedUser = sess.get(Users.class, Integer.valueOf(payload.get("userId").toString()));
			try {
				updatedUser.setValidIdPicture(payload.get("validIdPicture").toString());
				updatedUser.setValidIdNumber(payload.get("validIdNumber").toString());
				updatedUser.setValidIdType(payload.get("validIdType").toString());
				updatedUser.setRecentPicture(payload.get("recentPicture").toString());
			} catch (NullPointerException e) {
			}
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
			updatedTip.setTitle(farmingTip.getTitle());
			updatedTip.setDescription(farmingTip.getDescription());
			updatedTip.setLink(farmingTip.getLink());
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
			updatedComplaint.setIsResolved(farmerComplaint.getIsResolved());
			sess.merge(updatedComplaint);
			tx.commit();
			return updatedComplaint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Supplier Complaints
	public List<SupplierComplaint> selectSupplierComplaints() {
		List<SupplierComplaint> records = new ArrayList<>();
		// this is HQL so make supervisor to Supervisor and with ref var
		// if you make Supervisor lower case, it will throw an error
		String sql = "select * from supplier_complaint where active_deactive = 't' order by supplier_complaint_id";

		try (Session sess = sf.openSession()) {
			Query<SupplierComplaint> query = sess.createNativeQuery(sql, SupplierComplaint.class);
			records = query.getResultList();

			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}

	public SupplierComplaint updateIntoSupplierComplaint(SupplierComplaint supplierComplaint) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
		
			SupplierComplaint updatedComplaint = sess.get(SupplierComplaint.class, supplierComplaint.getSupplierComplaintId());
			updatedComplaint.setAdminReplyMessage(supplierComplaint.getAdminReplyMessage());
			if (updatedComplaint.getReadDate() == null) {
				updatedComplaint.setReadDate(supplierComplaint.getReadDate());				
			}
			
			updatedComplaint.setIsRead(supplierComplaint.getIsRead());
			updatedComplaint.setIsResolved(supplierComplaint.getIsResolved());
			sess.merge(updatedComplaint);
			tx.commit();
			return updatedComplaint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Course
	public List<Course> selectAllCourses() {
		List<Course> records = new ArrayList<>();
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

	public Course insertIntoCourses(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Course course = new Course();
			course.setCourseName(payload.get("courseName").toString());
			course.setDescription(payload.get("description").toString());
			course.setDurationInDays(Integer.valueOf(payload.get("durationInDays").toString()));
			course.setActiveDeactive(true);
			course.setLink(payload.get("link").toString());
			sess.persist(course);
			tx.commit();
			return course;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Course updateIntoCourses(Map<String, Object> payload) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Course updatedCourse = sess.get(Course.class, Integer.valueOf(payload.get("courseId").toString()));
			updatedCourse.setCourseName(payload.get("courseName").toString());
			updatedCourse.setDescription(payload.get("description").toString());
			updatedCourse.setDurationInDays(Integer.valueOf(payload.get("durationInDays").toString()));
			updatedCourse.setLink(payload.get("link").toString());
			sess.merge(updatedCourse);
			tx.commit();
			return updatedCourse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Course deleteCourse(Integer courseId) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Course course = sess.get(Course.class, courseId);
			course.setActiveDeactive(!course.isActiveDeactive());

			sess.merge(course);
			tx.commit();
			return course;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Admin Dashboard
	public long countValidatedFarmers() {
	    long count = 0; 
	    String sql = "SELECT COUNT(*) " +
	                 "FROM farmer f " +
	                 "INNER JOIN users u ON f.user_id = u.user_id " +
	                 "WHERE u.is_validated = true " + 
	                 "AND u.active_status = true";

	    try (Session session = sf.openSession()) {
	        Query<Long> query = session.createNativeQuery(sql, Long.class);
	        Long result = query.uniqueResult();
	        if (result != null) {
	            count = result;
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public long countNotValidatedFarmers() {
	    long count = 0;
	    String sql = "SELECT COUNT(*) " +
	                 "FROM farmer f " +
	                 "INNER JOIN users u ON f.user_id = u.user_id " +
	                 "WHERE u.is_validated = false";

	    try (Session session = sf.openSession()) {
	        Query<Long> query = session.createNativeQuery(sql, Long.class);
	        Long result = query.uniqueResult();
	        if (result != null) {
	            count = result;
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public long countValidatedSuppliers() {
	    long count = 0;
	    String sql = "SELECT COUNT(*) " +
	                 "FROM supplier s " +
	                 "INNER JOIN users u ON s.user_id = u.user_id " +
	                 "WHERE u.is_validated = true " + 
	                 "AND u.active_status = true";
	    try (Session session = sf.openSession()) {
	        Query<Long> query = session.createNativeQuery(sql, Long.class);
	        Long result = query.uniqueResult();
	        if (result != null) {
	            count = result;
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public long countNotValidatedSuppliers() {
	    long count = 0;
	    String sql = "SELECT COUNT(*) " +
	                 "FROM supplier s " +
	                 "INNER JOIN users u ON s.user_id = u.user_id " +
	                 "WHERE u.is_validated = false";

	    try (Session session = sf.openSession()) {
	        Query<Long> query = session.createNativeQuery(sql, Long.class);
	        Long result = query.uniqueResult();
	        if (result != null) {
	            count = result;
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public long countUnresolvedSupplierComplaints() {
	    long count = 0; 
	    String sql = "SELECT COUNT(*) " +
	                 "FROM supplier_complaint " +
	                 "WHERE is_resolved = false";

	    try (Session session = sf.openSession()) {
	        Query<Long> query = session.createNativeQuery(sql, Long.class);
	        Long result = query.uniqueResult();
	        if (result != null) {
	            count = result;
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}

	public long countUnresolvedFarmerComplaints() {
	    long count = 0; 
	    String sql = "SELECT COUNT(*) " +
	                 "FROM farmer_complaint " +
	                 "WHERE is_resolved = false";

	    try (Session session = sf.openSession()) {
	        Query<Long> query = session.createNativeQuery(sql, Long.class);
	        Long result = query.uniqueResult();
	        if (result != null) {
	            count = result;
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}

}
