package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.glassfish.jaxb.core.v2.model.core.ID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
   // Session session = Util.getSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(180), " +
                "lastName VARCHAR(180), " +
                "age SMALLINT)";
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error creating users table");
        }
        finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE Users";
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error dropping users table");
        }
        finally {
            session.close();
        }

    }

    @Override

    public void saveUser(String name, String lastName, byte age) {

        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: User cannot be save ");
        }


    }

    @Override
    public void removeUserById(long id) {
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if(user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: User cannot be remove ");
        }
            finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<User> users = null;
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery( "from User", User.class);
            users = query.list();
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: User cannot be get ");
        }
        finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: User cannot be clean ");
        }
        finally {
            session.close();
        }

    }
}
