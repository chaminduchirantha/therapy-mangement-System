package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

import java.net.URL;
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
    private TableView<?> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBalance;

    @FXML
    private ImageView user;

    PaymentBO paymentBO = new PaymentBOImpl();
    RegistrationBo registrationBo = new RegistrationBoImpl();
    PatientBO patientBO = new PatientBOImpl();
    TherapyProgramBO therapyProgramBO = new TherapyProgramBOImpl();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        PaymentDTO paymentDTO = new PaymentDTO(
                lblId.getText(),
                datePicker.getValue(),
                cmbMethod.getValue(),
                Double.parseDouble(txtAmount.getText()),
                Double.parseDouble(txtBalance.getText()),
                cmbEnrollment.getValue()
        );
        if (paymentBO.save(paymentDTO)) {
            showSuccessAlert("Registration Successfully.");
//            refreshPage();
        } else {
            showErrorAlert("Registration failed.");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbEnrollmentOnAction(ActionEvent event) {
        if (cmbEnrollment.getValue() == null) return;

        RegistrationDto registrationDto = registrationBo.getPatientById(cmbEnrollment.getValue());
        System.out.println(registrationDto);
        lblPatientId.setText(registrationDto != null ? registrationDto.getPatientId() : "Unknown");
        lblProgrammeId.setText(registrationDto != null ? registrationDto.getProgrammeId() : "Unknown");
    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    private void loadComboData() throws Exception {
        cmbEnrollment.setItems(FXCollections.observableArrayList(
                patientBO.getPatients().stream().map(PatientDTO::getPatientId).toList()
        ));

        cmbEnrollment.setItems(FXCollections.observableArrayList(
                therapyProgramBO.getPrograms().stream().map(TherapyProgramDTO::getProgramId).toList()
        ));

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

    private void loadMethod(){
        String[] method = {"Card Payment" , "Cash payment"};
        cmbMethod.getItems().addAll(method);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
           loadComboData();
           loadMethod();
        }catch (Exception e){

        }
    }
}
