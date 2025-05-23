package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapistBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.TherapistDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapistDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Therapist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapistBOImpl implements TherapistBO {
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.THERAPIST);

    @Override
    public boolean saveTherapist(TherapistDTO dto) {
        Therapist therapist = new Therapist(
                dto.getTherapistId(),
                dto.getName(),
                dto.getSpecialization(),
                dto.getContactNumber(),
                null,
                null
        );

        return therapistDAO.save(therapist);
    }

    @Override
    public boolean updateTherapist(TherapistDTO dto) {
        Therapist therapist = new Therapist(
                dto.getTherapistId(),
                dto.getName(),
                dto.getSpecialization(),
                dto.getContactNumber(),
                null,
                null
        );

        return therapistDAO.update(therapist);
    }

    @Override
    public boolean deleteTherapist(String id) {
        return therapistDAO.delete(id);
    }

    @Override
    public String getNextTherapistId() {
        Optional<String> lastTherapistId = therapistDAO.getLastId();

        if (lastTherapistId.isPresent()) {
            String lastID = lastTherapistId.get();
            int numericPart = Integer.parseInt(lastID.substring(1));
            numericPart++;
            return String.format("T%03d", numericPart);
        } else {
            return "T001";
        }
    }

    @Override
    public List<TherapistDTO> getTherapists() {
        List<Therapist> therapists = therapistDAO.getAll();
        List<TherapistDTO> therapistDTOS = new ArrayList<>();

        for (Therapist therapist : therapists) {
            TherapistDTO therapistDTO = new TherapistDTO();
            therapistDTO.setTherapistId(therapist.getTherapistId());
            therapistDTO.setName(therapist.getName());
            therapistDTO.setContactNumber(therapist.getContactNumber());
            therapistDTO.setSpecialization(therapist.getSpecialization());
            therapistDTOS.add(therapistDTO);
        }

        return therapistDTOS;
    }

    @Override
    public TherapistDTO getTherapistById(String id) {
        Optional<Therapist> byId = therapistDAO.findById(id);
        if (byId.isPresent()) {
            Therapist therapist = byId.get();
            TherapistDTO therapistDTO = new TherapistDTO();
            therapistDTO.setTherapistId(therapist.getTherapistId());
            therapistDTO.setName(therapist.getName());
            therapistDTO.setContactNumber(therapist.getContactNumber());
            therapistDTO.setSpecialization(therapist.getSpecialization());

            return therapistDTO;
        }
        return null;
    }
}
