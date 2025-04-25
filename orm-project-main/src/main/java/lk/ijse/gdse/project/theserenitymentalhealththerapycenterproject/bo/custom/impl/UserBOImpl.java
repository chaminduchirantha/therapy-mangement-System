package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.UserBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.UserDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.UserDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.USER);


    @Override
    public boolean saveUser(UserDTO dto) {
        User user = new User(
                dto.getUserId(),
                dto.getUserName(),
                dto.getPassword(),
                dto.getRole()
        );

        return userDAO.save(user);

    }

    @Override
    public boolean updateUser(UserDTO dto) {
        User user = new User(
                dto.getUserId(),
                dto.getUserName(),
                dto.getPassword(),
                dto.getRole()
        );

        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String id) {
        return userDAO.delete(id);
    }

    @Override
    public String getNextUserId() {
        Optional<String> lastId = userDAO.getLastId();

        if (lastId.isPresent()) {
            String lastID = lastId.get();
            int numericPart = Integer.parseInt(lastID.substring(1));
            numericPart++;
            return String.format("U%03d", numericPart);
        } else {
            return "U001";
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public UserDTO getUserById(String id) {
        Optional<User> byId = userDAO.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            UserDTO userDTO = new UserDTO();

            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());

            return userDTO;
        }
        return null;
    }
}
