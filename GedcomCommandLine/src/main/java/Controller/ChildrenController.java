package Controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import Entities.Children;

public class ChildrenController {
	protected SessionFactory sessionFactory;

	public ChildrenController() {
		setup();
	}
	
	public void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	

	public void create(Children children) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(children);

		session.getTransaction().commit();
		session.close();
		
		
	}
	public Children get(String id) {
		Session session = sessionFactory.openSession();
		Children child = session.get(Children.class, id);
		session.close();
		return child;
	}
	public List<Children> getAll() {
		Session session = sessionFactory.openSession();
		List<Children> res=session.createQuery("SELECT a FROM Children a", Children.class).getResultList();  
		session.close();
		return res;
	}
	public boolean check(String id) {
		Session session = sessionFactory.openSession();
		Children i = session.get(Children.class, id);
		if(i==null) {
			return true;
		}
		else {
			return false;
		}
	}
	public void update(Children i) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(i);

		session.getTransaction().commit();
		session.close();
	}
	protected void delete(String id) {
		Children i=new Children(id);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(i);
		session.getTransaction().commit();
		session.close();
	}
	public void exit() {
		if(sessionFactory!=null)
			sessionFactory.close();
		
	}
	

}
