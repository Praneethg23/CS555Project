package Controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import Entities.Families;


public class FamilyController {
	protected SessionFactory sessionFactory;

	public FamilyController() {
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
	public void create(Families fam) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(fam);

		session.getTransaction().commit();
		session.close();
	}
	public boolean check(String id) {
		Session session = sessionFactory.openSession();
		Families i = session.get(Families.class, id);
		if(i==null) {
			return true;
		}
		else {
			return false;
		}
	}
	public Families get(String id) {
		Session session = sessionFactory.openSession();
		Families fam = session.get(Families.class, id);
		session.close();
		return fam;
	}
	public List<Families> getAll(){
		Session session = sessionFactory.openSession();
		List<Families> res = session.createQuery("SELECT a FROM Families a", Families.class).getResultList(); 
		session.close();
		return res;
	}
	public void update(Families fam) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.update(fam);

		session.getTransaction().commit();
		session.close();
	}
	protected void delete(String id) {
		Families fam=new Families(id);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(fam);
		session.getTransaction().commit();
		session.close();
	}
	

}
