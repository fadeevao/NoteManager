package app.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class TestNote {
	
	public static void main(String args[]){
    	Configuration configuration = new Configuration().configure();
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	SessionFactory factory = configuration.buildSessionFactory(builder.build());
    	Session session = (Session) factory.openSession();
    	Note note = new Note();
    	note.setName("name");
    	session.beginTransaction();
    	session.save(note);
    	session.getTransaction().commit();
    }
}