package Controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import Entities.Individuals;

public class IndividualController {
	protected SessionFactory sessionFactory;

	public IndividualController() {
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

	public void exit() {
		if(sessionFactory!=null)
			sessionFactory.close();
	}
	public void create(Individuals i) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(i);

		session.getTransaction().commit();
		session.close();
	}
	public Individuals get(String id) {
		Session session = sessionFactory.openSession();
		Individuals i = session.get(Individuals.class, id);
		session.close();
		return i;
	}
	public List<Individuals> getAll(){
		Session session = sessionFactory.openSession();
		List<Individuals> res = session.createQuery("SELECT a FROM Individuals a", Individuals.class).getResultList();
		session.close();
		return res;
	}
	public boolean check(String id) {
		Session session = sessionFactory.openSession();
		Individuals i = session.get(Individuals.class, id);
		if(i==null) {
			return true;
		}
		else {
			return false;
		}
	}
	public void update(Individuals i) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(i);

		session.getTransaction().commit();
		session.close();
	}
	protected void delete(String id) {
		Individuals i=new Individuals(id);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(i);
		session.getTransaction().commit();
		session.close();
	}
	

}
