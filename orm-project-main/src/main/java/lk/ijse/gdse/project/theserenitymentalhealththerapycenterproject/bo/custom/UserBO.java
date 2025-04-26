package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.SuperBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapistDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO dto);
    boolean updateUser(UserDTO dto);
    boolean deleteUser(String id);
    String getNextUserId();
    List<UserDTO> getUsers();
    public String getUserRole(String id) ;
    UserDTO getUserById(String id);
}
