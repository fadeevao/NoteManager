package app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.Note;


@Service("dao")
public class NoteDAOImpl implements NoteDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private String hql;
	
	private Session session;

	private static final long serialVersionUID = -58245061055944046L;


	 public Session getSession()
     {
          if (sessionFactory.getCurrentSession() != null) {
        	  return  sessionFactory.getCurrentSession();
          } else {
        	  return sessionFactory.openSession();
          }
     }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Note> findAll() {
		
		Transaction transaction = null;
		session = getSession();
		try {
			transaction = session.beginTransaction();
			hql = "FROM Note";
			Query query = session.createQuery(hql);
			List results = query.list();
			transaction.commit();
			return results;
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void save(Note note) {
		session = getSession();
		session.beginTransaction();
		session.save(note);
		session.getTransaction().commit();
	}

	@Override
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

	@Override
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
			transaction.rollback();
			throw e;
		}
	}

	@Override
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
			transaction.rollback();
			throw e;
		}
	}
	
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
