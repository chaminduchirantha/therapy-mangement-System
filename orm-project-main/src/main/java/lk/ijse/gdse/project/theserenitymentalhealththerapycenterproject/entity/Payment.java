package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payments")
public class Payment implements SuperEntity {
    @Id
    private String paymentId;

    private LocalDate paymentDate;
    private double balance;
    private double amount;
    private String method;


    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    public Payment(String paymentId, String date, double balance, double amount, String method, Registration registration) {
    }
}
