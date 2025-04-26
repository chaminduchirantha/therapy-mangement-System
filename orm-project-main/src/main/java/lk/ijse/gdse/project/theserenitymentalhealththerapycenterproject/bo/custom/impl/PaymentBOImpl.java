package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.PaymentBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.PaymentDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PaymentDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapySessionDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.*;

import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.PAYEMENT);

    @Override
    public boolean save(PaymentDTO paymentDTO) {
        Registration registration = new Registration();
        registration.setRegistrationId(paymentDTO.getRegistrationID());


        Payment payment = new Payment(
                paymentDTO.getPaymentId(),
                paymentDTO.getDate(),
                paymentDTO.getBalance(),
                paymentDTO.getAmount(),
                paymentDTO.getMethod(),
                registration
        );

        return paymentDAO.save(payment);
    }

    @Override
    public boolean update(PaymentDTO dto) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public String getNextId() {
        return "";
    }

    @Override
    public List<PaymentDTO> getPayments() {
        return List.of();
    }

    @Override
    public PaymentDTO getPaymentById(String id) {
        Optional<Payment> paymentOn = paymentDAO.findById(id);
        if (paymentOn.isPresent()) {
            Payment payment = paymentOn.get();
            return new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getPaymentDate(),
                    payment.getMethod(),
                    payment.getAmount(),
                    payment.getBalance(),
                    payment.getRegistration().getRegistrationId()
            );
        }
        return null;
    }
}
