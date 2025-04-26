package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.SuperBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PatientDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PaymentDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.Payment;

import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean save(PaymentDTO dto);
    boolean update(PaymentDTO dto);
    boolean delete(String id);
    String getNextId();
    List<PaymentDTO> getPayments();
    PaymentDTO getPaymentById(String id);
}
