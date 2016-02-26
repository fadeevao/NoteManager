package app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Note;


@Service("dao")
public class NoteDao  {
	private static final Logger log = Logger.getLogger(NoteDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	private String hql;
	
	private Session session;

	private static final long serialVersionUID = -58245061055944046L;


	 public Session getSession()
     {
          if (sessionFactory.getCurrentSession() != null) {
        	  return  sessionFactory.getCurrentSession();
          } 
        
          return sessionFactory.openSession();
     }
	
	@SuppressWarnings("unchecked")
	public List<Note> findAll(long id) {
		
		Transaction transaction = null;
		session = getSession();
		try {
			transaction = session.beginTransaction();
			hql = "FROM Note where user_id=:id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List results = query.list();
			transaction.commit();
			return results;
		} catch (RuntimeException e) {
			transaction.rollback();
			log.error("Exception thrown when trying to retrive notes from database with id:" + id, e);
			throw e;
		}
	}

	public void save(Note note) {
		session = getSession();
		session.beginTransaction();
		session.save(note);
		session.getTransaction().commit();
	}

	public Note getNote(String name) {
		session = getSession();
		session.beginTransaction();
		hql = "FROM Note where name= :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		List results = query.list();

		session.getTransaction().commit();
		if (results.size()>0) {
			return (Note) results.get(0);
		} else {
			return null;
		}
	}

	public void deleteAllNotes() {
		
		Transaction transaction = null;
		session = getSession();
		try {
			transaction = session.beginTransaction();
			hql = "delete from Note";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
			transaction.commit();
		} catch (RuntimeException e) {
			log.error("Exception thrown when trying to delete all notes", e);
			transaction.rollback();
			throw e;
		}
	}
	
	/*
	 * Deletes the notes that user has selected in the UI
	 * @param selectedItems  array that contains ids of user-selected items
	 */
	public void deleteNotes(String[] selectedItems) {
		
		List<Long> ids = convertStringArrayToLong(selectedItems);
		Transaction transaction = null;
		session = getSession();
		try {
		transaction = session.beginTransaction();
		hql = "delete FROM Note where id IN  (:ids)";
		Query query = session.createQuery(hql);
		query.setParameterList("ids", ids);
		query.executeUpdate();
		transaction.commit();
		
		} catch (RuntimeException e) {
			log.error("Exception thrown when trying to delete user selected notes", e);
			transaction.rollback();
			throw e;
		}
	}
	
	/*
	 * Helper method to convert array of String objects into array of Long objects
	 * @param array to convert from
	 */
	private List<Long> convertStringArrayToLong(String[] array) {
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < array.length; i++) {
			  list.add(Long.valueOf(array[i]));
			}
		return list;
		
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
