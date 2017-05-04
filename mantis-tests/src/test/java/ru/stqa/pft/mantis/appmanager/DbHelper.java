package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.UserData;

import java.util.List;

public class DbHelper {

  private final SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
  }

  public UserData currentUser() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> results = session.createQuery("from UserData where id!='1'").list();
    UserData result = results.iterator().next();
    session.getTransaction().commit();
    session.close();
    return new UserData().withId(result.getId()).withName(result.getName()).withEmail(result.getEmail());
  }




}

