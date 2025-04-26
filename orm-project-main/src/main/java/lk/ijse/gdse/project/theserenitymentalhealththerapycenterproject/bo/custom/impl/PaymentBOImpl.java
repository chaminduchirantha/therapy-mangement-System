package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.PaymentBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.PaymentDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.RegistrationDao;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl.RegistrationDAOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PaymentDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.RegistrationDto;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapySessionDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.entity.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.PAYEMENT);
    RegistrationDao registrationDao = new RegistrationDAOImpl();
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
    public boolean update(PaymentDTO paymentDTO) {
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

        return paymentDAO.update(payment);
    }

    @Override
    public boolean delete(String id) {
        return paymentDAO.delete(id);
    }

    @Override
    public String getNextPaymentId() throws SQLException, IOException {
        Optional<String> lastId = paymentDAO.getLastId();

        if (lastId.isPresent()) {
            String lastID = lastId.get();
            int numericPart = Integer.parseInt(lastID.substring(2));
            numericPart++;
            return String.format("MY%03d", numericPart);
        } else {
            return "MY001";
        }
    }

    @Override
    public List<PaymentDTO> getPayments() {
        List<Payment> allPayments = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();

        for (Payment payment : allPayments) {
            PaymentDTO dto = new PaymentDTO();
            dto.setPaymentId(payment.getPaymentId());
            dto.setDate(payment.getPaymentDate());
            dto.setBalance(payment.getBalance());
            dto.setAmount(payment.getAmount());
            dto.setMethod(payment.getMethod());

            if (payment.getRegistration() != null) {
                dto.setRegistrationID(payment.getRegistration().getRegistrationId());
            } else {
                dto.setRegistrationID(null);
                System.err.println("Warning: Registration is null for payment ID " + payment.getPaymentId());
            }

            paymentDTOList.add(dto);
        }

        return paymentDTOList;
    }

    @Override
    public List<RegistrationDto> getRegistration() {
        List<Registration> allRegistrations = registrationDao.getAll();
        List<RegistrationDto> registrationDtos = new ArrayList<>();

        for (Registration registration : allRegistrations) {
            RegistrationDto dto = new RegistrationDto();
            dto.setRegistrationId(registration.getRegistrationId());
            dto.setRegistrationDate(registration.getRegistrationDate());
            dto.setProgrammeFees(registration.getProgrammeFees());

            if (registration.getPatient() != null) {
                dto.setPatientId(registration.getPatient().getPatientId());
            } else {
                dto.setPatientId(null);
                System.err.println("Warning: Patient is null for registration ID " + registration.getRegistrationId());
            }

            if (registration.getTherapyPrograms() != null) {
                dto.setProgrammeId(registration.getTherapyPrograms().getProgramId());
            } else {
                dto.setProgrammeId(null);
                System.err.println("Warning: TherapyProgram is null for registration ID " + registration.getRegistrationId());
            }

            registrationDtos.add(dto);
        }

        return registrationDtos;
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
                    payment.getRegistration() != null ? payment.getRegistration().getRegistrationId() : null
            );
        }
        return null;
    }
}
