package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.SuperBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PatientDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PaymentDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Payment;

import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDTO dto);
    boolean updatePayment(PaymentDTO dto);
    boolean deletePayment(String id);
    String getNextPaymentId();
    List<PaymentDTO> getPayments();
    PaymentDTO getPaymentById(String id);
}
