package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.CrudDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.User;
import org.hibernate.Session;

import java.util.Optional;

public interface UserDAO extends CrudDAO<User, String> {
    public Optional<String> getRoleById(String id) ;
}
