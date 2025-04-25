package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.SuperBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.RegistrationDto;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationBo extends SuperBO {
    public boolean save(RegistrationDto registrationDto) ;
    public boolean update(RegistrationDto registrationDto) ;
    public boolean delete(String s) ;
    public Optional<Registration> findById(String s) ;
    public String getNextRegistrationId() ;
    public List<RegistrationDto> getAll() ;
}
