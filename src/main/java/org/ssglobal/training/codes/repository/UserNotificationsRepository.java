package org.ssglobal.training.codes.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.models.UserNotifications;

@Repository
public class UserNotificationsRepository {
	
	@Autowired
    private SessionFactory sf;

	// User Notifications
	public List<UserNotifications> selectAllNotificationsOfUserByUserId(Integer userId) {
		List<UserNotifications> records = new ArrayList<>();
		String sql = "select * from user_notifications where user_id = :user_id order by date_created desc";

		try (Session sess = sf.openSession()) {
			Query<UserNotifications> query = sess.createNativeQuery(sql, UserNotifications.class);
			query.setParameter("user_id", userId);
			records = query.getResultList();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Collections.unmodifiableList(records);
	}

	public List<UserNotifications> updateAllNotificationsToReadOfUserByUserId(Integer userId) {
		List<UserNotifications> records = new ArrayList<>();
		String sql = "select * from user_notifications where user_id = :user_id";
		
		Transaction tx = null;
		try (Session sess = sf.openSession()) {
			tx = sess.beginTransaction();
			Query<UserNotifications> query = sess.createNativeQuery(sql, UserNotifications.class);
			query.setParameter("user_id", userId);
			records = query.getResultList();
			
			records.forEach(data -> {
				UserNotifications notification = sess.get(UserNotifications.class, data.getNotificationId());
				notification.setIsRead(true);
				sess.merge(notification);
			});
			tx.commit();
			return records;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
