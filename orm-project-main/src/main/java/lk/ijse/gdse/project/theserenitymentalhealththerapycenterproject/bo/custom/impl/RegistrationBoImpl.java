package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.RegistrationBo;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl.RegistrationDAOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.RegistrationDto;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Patient;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Registration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.TherapyProgram;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrationBoImpl implements RegistrationBo {
    RegistrationDAOImpl registrationDAO = (RegistrationDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.daoType.REGISTRATION);

    @Override
    public boolean save(RegistrationDto registrationDto) {
        Patient patient = new Patient();
        patient.setPatientId(registrationDto.getPatientId());

        TherapyProgram program = new TherapyProgram();
        program.setProgramId(registrationDto.getProgrammeId());

        // Now create Registration with those entities
        Registration registration = new Registration(
                registrationDto.getRegistrationId(),
                registrationDto.getRegistrationDate(),
                registrationDto.getProgrammeFees(),
                patient,
                program
        );

        return registrationDAO.save(registration);
    }

    @Override
    public boolean update(RegistrationDto registrationDto) {
        Patient patient = new Patient();
        patient.setPatientId(registrationDto.getPatientId());

        TherapyProgram program = new TherapyProgram();
        program.setProgramId(registrationDto.getProgrammeId());

        Registration registration = new Registration(
                registrationDto.getRegistrationId(),
                registrationDto.getRegistrationDate(),
                registrationDto.getProgrammeFees(),
                patient,
                program
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

            if (registration.getPatient() != null) {
                registrationDto.setPatientId(registration.getPatient().getPatientId());
            } else {
                registrationDto.setPatientId(null);
                System.err.println("Warning: Patient is null for registration ID " + registration.getRegistrationId());
            }

            if (registration.getTherapyPrograms() != null) {
                registrationDto.setProgrammeId(registration.getTherapyPrograms().getProgramId());
            } else {
                registrationDto.setProgrammeId(null);
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

    @Override
    public RegistrationDto getPatientById(String id){
        Optional<Registration> registrationOn = registrationDAO.findById(id);
        if (registrationOn.isPresent()) {
            Registration registration = registrationOn.get();
            return new RegistrationDto(
                    registration.getRegistrationId(),
                    registration.getRegistrationDate(),
                    registration.getProgrammeFees(),
                    registration.getPatient().getPatientId(),
                    registration.getTherapyPrograms().getProgramId()
            );
        }
        return null;
    }
}
