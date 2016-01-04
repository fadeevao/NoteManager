package app.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.Note;


@Service("dao")
public class NoteDAOImpl implements NoteDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	String hql;

	private static final long serialVersionUID = -58245061055944046L;


	 public Session getSession()
     {
          return sessionFactory.getCurrentSession();
     }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Note> findAll() {
		
		Transaction transaction = null;
		try {
			transaction = getSession().beginTransaction();
			hql = "FROM Note";
			Query query = getSession().createQuery(hql);
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
		getSession().beginTransaction();
		getSession().save(note);
		getSession().getTransaction().commit();
	}

	@Override
	public Note getNote(String name) {
		getSession().beginTransaction();
		hql = "FROM Note where name= :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		List results = query.list();

		getSession().getTransaction().commit();
		return (Note) results.get(0);
	}

	@Override
	public void deleteAllNotes() {
		
		Transaction transaction = null;
		try {
			transaction = getSession().beginTransaction();
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
	public void deleteNotesByName(List<String> names) {

		Transaction transaction = null;
		try {
		transaction = getSession().beginTransaction();
		hql = "delete FROM Note where name IN  (:names)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("names", names);
		query.executeUpdate();
		transaction.commit();
		
		} catch (RuntimeException e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void deleteNote(String name) {
		// TODO Auto-generated method stub
		
	}

}
