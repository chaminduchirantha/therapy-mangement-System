package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.RegistrationBo;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl.RegistrationDAOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.RegistrationDto;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapyProgramDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Therapist;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrationBoImpl implements RegistrationBo {
    RegistrationDAOImpl registrationDAO = new RegistrationDAOImpl();

    @Override
    public boolean save(RegistrationDto registrationDto) {
        Registration registration = new Registration(
                registrationDto.getRegistrationId(),
                registrationDto.getRegistrationDate(),
                registrationDto.getProgrammeFees(),
                null,
                null
        );

        return registrationDAO.save(registration);
    }

    @Override
    public boolean update(RegistrationDto registrationDto) {
        Registration registration = new Registration(
                registrationDto.getRegistrationId(),
                registrationDto.getRegistrationDate(),
                registrationDto.getProgrammeFees(),
                null,
                null
        );

        return registrationDAO.save(registration);
    }

    @Override
    public boolean delete(String s) {
        return registrationDAO.delete(s);
    }

    @Override
    public Optional<Registration> findById(String s) {

        return Optional.empty();
    }

    @Override
    public List<RegistrationDto> getAll() {

        List<Registration> all = registrationDAO.getAll();
        List<RegistrationDto> registrationDtos = new ArrayList<>();

        for (Registration registration : all) {
            RegistrationDto registrationDto = new RegistrationDto();
            registrationDto.setRegistrationId(registration.getRegistrationId());
            registrationDto.setRegistrationDate(registration.getRegistrationDate());
            registrationDto.setProgrammeFees(registration.getProgrammeFees());
            registrationDto.setPatientId(registration.getPatient().getPatientId());
            registrationDto.setProgrammeId(registration.getTherapyPrograms().getProgramId());
            registrationDtos.add(registrationDto);
        }
        return registrationDtos;
    }

    @Override
    public String getNextRegistrationId() {
        Optional<String> lastId = registrationDAO.getLastId();
        if (lastId.isPresent() && lastId.get().matches("E\\d{3}")) {
            int numeric = Integer.parseInt(lastId.get().substring(1));
            return String.format("E%03d", numeric + 1);
        }
        return "E001";
    }

}
