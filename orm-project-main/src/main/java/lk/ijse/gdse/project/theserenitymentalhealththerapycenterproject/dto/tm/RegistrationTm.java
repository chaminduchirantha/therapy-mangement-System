package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationTm {
    private String registrationId;
    private LocalDate registrationDate;
    private double programmeFees;
    private String programmeId;
    private String patientId;
}
