package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.PaymentBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.PaymentDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PaymentDTO;

import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDao(DAOFactory.daoType.PAYEMENT);

    @Override
    public boolean savePayment(PaymentDTO dto) {
     return false;
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) {
        return false;
    }

    @Override
    public boolean deletePayment(String id) {
        return false;
    }

    @Override
    public String getNextPaymentId() {
        return "";
    }

    @Override
    public List<PaymentDTO> getPayments() {
        return List.of();
    }

    @Override
    public PaymentDTO getPaymentById(String id) {
        return null;
    }
}
