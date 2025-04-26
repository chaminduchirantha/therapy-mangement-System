package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentDTO {
    private String paymentId;
    private LocalDate date;
    private String method;
    private double amount;
    private double balance;
    private String registrationID;


}
