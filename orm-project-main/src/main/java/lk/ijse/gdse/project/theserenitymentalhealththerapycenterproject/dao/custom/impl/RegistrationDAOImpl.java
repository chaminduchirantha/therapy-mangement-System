package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.config.FactoryConfiguration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.EnrollmentDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class RegistrationDAOImpl implements EnrollmentDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration entity) {
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
    public boolean update(lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration entity) {
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
            lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration registration = session.get(lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration.class, s);
            session.remove(registration);
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
    public Optional<lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration> findById(String s) {
        Session session = factoryConfiguration.getSession();
        lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration registration = session.get(lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration.class, s);
        session.close();

        if (registration == null) {
            return Optional.empty();
        }
        return Optional.of(registration);
    }

    @Override
    public List<lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration> query = session.createQuery("from Registration", lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration.class);
        return query.list();
    }

    @Override
    public Optional<String> getLastId() {
        Session session = factoryConfiguration.getSession();

        String lastId = session
                .createQuery("SELECT e.id FROM Registration e ORDER BY e.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();
        return Optional.ofNullable(lastId);
    }
}
