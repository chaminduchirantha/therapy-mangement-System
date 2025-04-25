package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.config.FactoryConfiguration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.CrudDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;

public interface RegistrationDao extends CrudDAO<Registration, String> {
    public String getNextId() throws SQLException, IOException ;

}
