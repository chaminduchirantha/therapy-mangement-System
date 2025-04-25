package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapySessionTM {
    private String sessionId;
    private LocalDate date;
    private String patId;
    private String programmeId;
    private String therapistId;
}
