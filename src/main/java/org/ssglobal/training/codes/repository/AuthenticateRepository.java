package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.Users;

@Repository
public class AuthenticateRepository {
	
	@Autowired(required = false)
    private SessionFactory sf;
	
	public Optional<Users> findOneByUsername(String username) {
		// Named Parameter
		String sql = "SELECT * FROM users WHERE username = :username";

		try (Session sess = sf.openSession()) {
			Query<Users> query = sess.createNativeQuery(sql, Users.class);
			query.setParameter("username", username);
			Users record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public Users updatePassword(Users user) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();

			Users updatedUser = sess.get(Users.class, user.getUserId());
			updatedUser.setPassword(user.getPassword());
			updatedUser = sess.merge(updatedUser);
			tx.commit();
			return updatedUser;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
