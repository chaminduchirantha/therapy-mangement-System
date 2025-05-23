package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.config.FactoryConfiguration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.UserDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.TherapySession;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(User entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(User entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(String s) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User entity = session.get(User.class, s);
            session.remove(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findById(String s) {
        Session session = factoryConfiguration.getSession();
        User user = session.get(User.class, s);
        session.close();

        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public List<User> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<User> query = session.createQuery("from User", User.class);
        return query.list();
    }

    @Override
    public Optional<String> getLastId() {
        Session session = factoryConfiguration.getSession();

        String lastId = session
                .createQuery("SELECT u.id FROM User u ORDER BY u.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();
        return Optional.ofNullable(lastId);
    }

    @Override
    public Optional<String> getRoleById(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            String role = session
                    .createQuery("SELECT u.role FROM User u WHERE u.id = :id", String.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return Optional.ofNullable(role);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
