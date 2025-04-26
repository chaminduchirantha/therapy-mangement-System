package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentTM {
    private String paymentId;
    private String date;
    private String method;
    private double amount;
    private double balance;
    private String registrationID;

}
