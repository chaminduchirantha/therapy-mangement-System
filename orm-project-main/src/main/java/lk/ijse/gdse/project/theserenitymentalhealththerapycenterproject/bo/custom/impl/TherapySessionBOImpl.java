package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapySessionBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.*;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.*;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapySessionBOImpl implements TherapySessionBO {

    private final TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.THERAPYSESSION);
    private final TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.THERAPIST);
    private final TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.THERAPYPROGRAMME);
    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.PATIENT);

    @Override
    public boolean saveSession(TherapySessionDTO dto) {
        Optional<Patient> patientOpt = patientDAO.findById(dto.getPatId());
        Optional<Therapist> therapistOpt = therapistDAO.findById(dto.getTherapistId());
        Optional<TherapyProgram> programOpt = therapyProgramDAO.findById(dto.getProgrammeId());

        if (patientOpt.isEmpty() || therapistOpt.isEmpty() || programOpt.isEmpty()) return false;

        TherapySession session = new TherapySession(
                dto.getSessionId(),
                dto.getDate(),
                patientOpt.get(),
                therapistOpt.get(),
                programOpt.get()
        );

        return therapySessionDAO.save(session);
    }

    @Override
    public boolean updateSession(TherapySessionDTO dto) {
        Optional<Patient> patientOpt = patientDAO.findById(dto.getPatId());
        Optional<Therapist> therapistOpt = therapistDAO.findById(dto.getTherapistId());
        Optional<TherapyProgram> programOpt = therapyProgramDAO.findById(dto.getProgrammeId());

        if (patientOpt.isEmpty() || therapistOpt.isEmpty() || programOpt.isEmpty()) return false;

        TherapySession session = new TherapySession(
                dto.getSessionId(),
                dto.getDate(),
                patientOpt.get(),
                therapistOpt.get(),
                programOpt.get()
        );

        return therapySessionDAO.update(session);
    }

    @Override
    public boolean deleteSession(String id) {
        return therapySessionDAO.delete(id);
    }

    @Override
    public String getNextSessionId() {
        Optional<String> lastId = therapySessionDAO.getLastId();
        if (lastId.isPresent() && lastId.get().matches("S\\d{3}")) {
            int numeric = Integer.parseInt(lastId.get().substring(1));
            return String.format("S%03d", numeric + 1);
        }
        return "S001";
    }

    @Override
    public List<TherapySessionDTO> getSessions() {
        List<TherapySessionDTO> dtos = new ArrayList<>();
        for (TherapySession session : therapySessionDAO.getAll()) {
            dtos.add(new TherapySessionDTO(
                    session.getSessionId(),
                    session.getSessionDate(),
                    session.getPatient().getPatientId(),
                    session.getTherapist().getTherapistId(),
                    session.getProgram().getProgramId()
            ));
        }
        return dtos;
    }

    @Override
    public TherapySessionDTO getSessionById(String id) {
        Optional<TherapySession> sessionOpt = therapySessionDAO.findById(id);
        if (sessionOpt.isPresent()) {
            TherapySession session = sessionOpt.get();
            return new TherapySessionDTO(
                    session.getSessionId(),
                    session.getSessionDate(),
                    session.getPatient().getPatientId(),
                    session.getTherapist().getTherapistId(),
                    session.getProgram().getProgramId()
            );
        }
        return null;
    }

    @Override
    public List<TherapyProgramDTO> getPrograms() {
        List<TherapyProgramDTO> dtos = new ArrayList<>();
        for (TherapyProgram program : therapyProgramDAO.getAll()) {
            dtos.add(new TherapyProgramDTO(
                    program.getProgramId(),
                    program.getName(),
                    program.getDurationInWeeks(),
                    program.getFee(),
                    program.getTherapist().getTherapistId()
            ));
        }
        return dtos;
    }

    @Override
    public List<PatientDTO> getPatients() {
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient patient : patientDAO.getAll()) {
            dtos.add(new PatientDTO(
                    patient.getPatientId(),
                    patient.getFullName(),
                    patient.getEmail(),
                    patient.getPhoneNumber(),
                    patient.getMedicalHistory()
            ));
        }
        return dtos;
    }

    @Override
    public List<TherapistDTO> getTherapists() {
        List<TherapistDTO> dtos = new ArrayList<>();
        for (Therapist therapist : therapistDAO.getAll()) {
            dtos.add(new TherapistDTO(
                    therapist.getTherapistId(),
                    therapist.getName(),
                    therapist.getContactNumber(),
                    therapist.getSpecialization()
            ));
        }
        return dtos;
    }
}
