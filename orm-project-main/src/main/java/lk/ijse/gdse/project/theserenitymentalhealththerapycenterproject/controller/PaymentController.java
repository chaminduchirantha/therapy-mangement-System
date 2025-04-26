package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.PatientBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.PaymentBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.RegistrationBo;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.PatientBOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.PaymentBOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.RegistrationBoImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.*;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm.PaymentTM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbEnrollment;

    @FXML
    private ComboBox<String> cmbMethod;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMethod;

    @FXML
    private TableColumn<?, ?> colRegistrationId;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblId;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblProgrammeId;

    @FXML
    private AnchorPane paymentAnchorpane;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBalance;

    @FXML
    private ImageView user;

    PaymentBO paymentBO = new PaymentBOImpl();

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            PaymentDTO paymentDTO = new PaymentDTO(
                    lblId.getText(),
                    datePicker.getValue(),
                    cmbMethod.getValue(),
                    Double.parseDouble(txtAmount.getText()),
                    Double.parseDouble(txtBalance.getText()),
                    cmbEnrollment.getValue()
            );

            if (paymentBO.save(paymentDTO)) {
                showSuccessAlert("Payment saved successfully.");
                refreshPage();
            } else {
                showErrorAlert("Failed to save payment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error while saving payment: " + e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            PaymentDTO paymentDTO = new PaymentDTO(
                    lblId.getText(),
                    datePicker.getValue(),
                    cmbMethod.getValue(),
                    Double.parseDouble(txtAmount.getText()),
                    Double.parseDouble(txtBalance.getText()),
                    cmbEnrollment.getValue()
            );

            if (paymentBO.update(paymentDTO)) {
                showSuccessAlert("Payment updated successfully.");
                refreshPage();
            } else {
                showErrorAlert("Failed to update payment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error while updating payment: " + e.getMessage());
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String paymentId = lblId.getText();
            if (paymentBO.delete(paymentId)) {
                showSuccessAlert("Payment deleted successfully.");
                refreshPage();
            } else {
                showErrorAlert("Failed to delete payment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error while deleting payment: " + e.getMessage());
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void cmbEnrollmentOnAction(ActionEvent event) {
        String registrationId = cmbEnrollment.getValue();
        if (registrationId == null) return;

        RegistrationDto registrationDto = paymentBO.getRegistration()
                .stream()
                .filter(dto -> dto.getRegistrationId().equals(registrationId))
                .findFirst()
                .orElse(null);

        if (registrationDto != null) {
            lblPatientId.setText(registrationDto.getPatientId());
            lblProgrammeId.setText(String.valueOf(registrationDto.getProgrammeFees()));
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        PaymentTM selected = tblPayment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(selected.getPaymentId());
            datePicker.setValue(selected.getDate());
            cmbMethod.setValue(selected.getMethod());
            txtAmount.setText(String.valueOf(selected.getAmount()));
            txtBalance.setText(String.valueOf(selected.getBalance()));
            cmbEnrollment.setValue(selected.getRegistrationID());
        }
    }

    private void loadComboData() {
        List<RegistrationDto> registrations = paymentBO.getRegistration();
        ObservableList<String> regIds = FXCollections.observableArrayList();
        for (RegistrationDto dto : registrations) {
            regIds.add(dto.getRegistrationId());
        }
        cmbEnrollment.setItems(regIds);
    }

    private void loadMethods() {
        cmbMethod.setItems(FXCollections.observableArrayList("Card Payment", "Cash Payment"));
    }

    private void loadPaymentsToTable() {
        List<PaymentDTO> paymentDTOList = paymentBO.getPayments();
        ObservableList<PaymentTM> paymentTMs = FXCollections.observableArrayList();

        for (PaymentDTO dto : paymentDTOList) {
            paymentTMs.add(new PaymentTM(
                    dto.getPaymentId(),
                    dto.getDate(),
                    dto.getMethod(),
                    dto.getAmount(),
                    dto.getBalance(),
                    dto.getRegistrationID()
            ));
        }

        tblPayment.setItems(paymentTMs);
    }

    private void refreshPage() {
        clearFields();
        loadPaymentsToTable();
        try {
            lblId.setText(paymentBO.getNextPaymentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        lblId.setText("");
        datePicker.setValue(null);
        cmbMethod.setValue(null);
        txtAmount.clear();
        txtBalance.clear();
        cmbEnrollment.setValue(null);
        lblPatientId.setText("");
        lblProgrammeId.setText("");
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationID"));

        try {
            loadComboData();
            loadMethods();
            loadPaymentsToTable();
            lblId.setText(paymentBO.getNextPaymentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}