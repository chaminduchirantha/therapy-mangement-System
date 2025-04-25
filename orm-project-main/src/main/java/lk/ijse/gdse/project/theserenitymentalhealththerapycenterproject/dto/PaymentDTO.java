package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentDTO {
    private String paymentId;
    private String date;
    private String enrollment;
    private double cost;
    private String patId;
    private String patName;
    private double amount;
    private String type;

}
