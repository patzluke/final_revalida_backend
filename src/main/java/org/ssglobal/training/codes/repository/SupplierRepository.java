package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
}
