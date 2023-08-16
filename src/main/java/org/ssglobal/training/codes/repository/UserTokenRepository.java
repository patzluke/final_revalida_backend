package org.ssglobal.training.codes.repository;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.UserToken;

@Repository
public class UserTokenRepository {
	
	@Autowired(required = false)
	private SessionFactory sf;
	
	public Optional<UserToken> findOneByUserId(Integer userId) {
		// Named Parameter
		String sql = "SELECT * FROM user_tokens WHERE user_id = :user_id";

		try (Session sess = sf.openSession()) {
			Query<UserToken> query = sess.createNativeQuery(sql, UserToken.class);
			query.setParameter("user_id", userId);
			UserToken record = query.getSingleResultOrNull();
			
			return Optional.ofNullable(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Optional<UserToken> findOneByUserIdAndToken(Integer userId, String token) {
		// Named Parameter
		String sql = "SELECT * FROM user_tokens WHERE user_id = :user_id and token = :token";

		try (Session sess = sf.openSession()) {
			Query<UserToken> query = sess.createNativeQuery(sql, UserToken.class);
			query.setParameter("user_id", userId);
			query.setParameter("token", token);
			UserToken record = query.getSingleResultOrNull();

			return Optional.of(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public boolean createToken(UserToken userToken) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();

			sess.persist(userToken);

			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Optional<UserToken> deleteById(Integer userId) {
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();

			UserToken sup = sess.get(UserToken.class, userId);
			sess.remove(sup);

			tx.commit();
			return Optional.ofNullable(sup);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}
}
