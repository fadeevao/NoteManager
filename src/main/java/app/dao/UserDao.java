package app.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.User;

@Service("userDao")
public class UserDao {

		
	@Autowired
	private SessionFactory sessionFactory;

	private String hql;
	
	private Session session;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * Used when a user tries to log in to check whether the provided user details and generated hash match database records
	 * @param username
	 * @param hash
	 */
	public boolean isValidUser(String username, String hash) {
		Transaction transaction= null;
		session = getSession();
			
		try {
			transaction = session.beginTransaction();
			hql = "FROM User where username=:username and hash=:hash";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("hash", hash);
			List<User> results = query.list();
			transaction.commit();
			if (results.size()>0) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}
	
	public void saveUser(User user) {
		Transaction transaction= null;
		session = getSession();
			
		try {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}
	
	public long getId(String username) {
		Transaction transaction= null;
		session = getSession();
			
		try {
			transaction = session.beginTransaction();
			hql = "FROM User where username=:username";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			List<User> results = query.list();
			transaction.commit();
			return results.get(0).getId();
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	} 
	
	/*
	 * Checks whether the username that the user picked has already been taken by someone else 
	 * @parma username 
	 */
	public boolean doesUsernamerExist(String username) {
		Transaction transaction= null;
		session = getSession();
			
		try {
			transaction = session.beginTransaction();
			hql = "FROM User where username=:username";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			List<User> results = query.list();
			transaction.commit();
			if (results.size()>0) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}
	
	/*
	 * Returns salt for some specific username
	 * @param username
	 */
	public String getSalt(String username) {
		Transaction transaction= null;
		session = getSession();
			
		try {
			transaction = session.beginTransaction();
			hql = "select salt FROM User where username=:username";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			List<String> results = query.list();
			transaction.commit();
			return results.get(0);
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
