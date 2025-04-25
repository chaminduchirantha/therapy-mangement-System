package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.SuperBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.*;

import java.util.List;

public interface TherapySessionBO extends SuperBO {
    boolean saveSession(TherapySessionDTO dto);
    boolean updateSession(TherapySessionDTO dto);
    boolean deleteSession(String id);
    String getNextSessionId();
    List<TherapySessionDTO> getSessions();
    TherapySessionDTO getSessionById(String id);
    List<TherapyProgramDTO> getPrograms();
    List<PatientDTO> getPatients();
    List<TherapistDTO> getTherapists();
}
