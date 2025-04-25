package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.RegistrationBo;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.config.FactoryConfiguration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl.RegistrationDAOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.RegistrationDto;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapyProgramDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Therapist;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrationBoImpl implements RegistrationBo {
    RegistrationDAOImpl registrationDAO = (RegistrationDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.daoType.REGISTRATION);

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

        return registrationDAO.update(registration);
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

            // Check if patient is not null
            if (registration.getPatient() != null) {
                registrationDto.setPatientId(registration.getPatient().getPatientId());
            } else {
                registrationDto.setPatientId(null); // or handle accordingly
                System.err.println("Warning: Patient is null for registration ID " + registration.getRegistrationId());
            }

            // Check if therapy program is not null
            if (registration.getTherapyPrograms() != null) {
                registrationDto.setProgrammeId(registration.getTherapyPrograms().getProgramId());
            } else {
                registrationDto.setProgrammeId(null); // or handle accordingly
                System.err.println("Warning: TherapyProgram is null for registration ID " + registration.getRegistrationId());
            }

            registrationDtos.add(registrationDto);
        }
        return registrationDtos;
    }


    @Override
    public String getNextId() throws SQLException, IOException {
       return registrationDAO.getNextId();
    }


}
