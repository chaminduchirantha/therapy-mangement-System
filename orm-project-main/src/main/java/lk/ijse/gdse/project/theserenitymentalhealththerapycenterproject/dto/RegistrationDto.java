package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationDto {
    private String registrationId;
    private LocalDate registrationDate;
    private double programmeFees;
    private String programmeId;
    private String patientId;

}
