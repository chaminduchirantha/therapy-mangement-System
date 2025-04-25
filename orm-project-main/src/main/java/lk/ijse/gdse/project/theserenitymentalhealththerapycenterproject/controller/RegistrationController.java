package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.BOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.PatientBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.RegistrationBo;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.PatientBOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.RegistrationBoImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.PatientDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.RegistrationDto;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapyProgramDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm.RegistrationTm;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm.TherapistTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {




    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbPatient;

    @FXML
    private ComboBox<String> cmbProgram;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPatient;

    @FXML
    private TableColumn<?, ?> colProgrammeFees;

    @FXML
    private TableColumn<?, ?> colProgrammeId;

    @FXML
    private DatePicker datePicker;


    @FXML
    private TextField txtReId;


    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblProgramFees;

    @FXML
    private Label lblProgramName;

    @FXML
    private TableView<RegistrationTm> tblEnrollment;

    @FXML
    private AnchorPane registrationAnchorpane;

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBo(BOFactory.boType.PATIENT);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBo(BOFactory.boType.THERAPYPROGRAMME);
    RegistrationBo registrationBo = (RegistrationBo) BOFactory.getInstance().getBo(BOFactory.boType.REGISTRATION);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
//        if (!validateInputs()) return;

        try {
            String id = txtReId.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                boolean isDeleted = registrationBo.delete(id);
                if (isDeleted) {
                    showSuccessAlert("Registration deleted successfully.");
                    refreshPage();
                } else {
                    showErrorAlert("Failed to delete registration.");
                }
            }

        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                txtReId.getText(),
                datePicker.getValue(),
                Double.parseDouble(lblProgramFees.getText()),
                cmbProgram.getValue(),
                cmbPatient.getValue()
                );
        if (registrationBo.save(registrationDto)) {
            showSuccessAlert("Registration Successfully.");
            refreshPage();
        } else {
            showErrorAlert("Registration failed.");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                txtReId.getText(),
                datePicker.getValue(),
                Double.parseDouble(lblProgramFees.getText()),
                cmbProgram.getValue(),
                cmbPatient.getValue()
        );
        if (registrationBo.update(registrationDto)) {
            showSuccessAlert("Session Update.");
            refreshPage();
        } else {
            showErrorAlert("update failed.");
        }
    }

    @FXML
    void cmbPatientOnAction(ActionEvent event) {
        if (cmbPatient.getValue() == null) return;
        PatientDTO dto = patientBO.getPatientById(cmbPatient.getValue());
        lblPatientName.setText(dto != null ? dto.getFullName() : "Unknown");
    }

    @FXML
    void cmbProgramOnAction(ActionEvent event) {
        if (cmbProgram.getValue() == null) return;

        TherapyProgramDTO dto = therapyProgramBO.getProgramById(cmbProgram.getValue());
        lblProgramName.setText(dto != null ? dto.getName() : "Unknown");
        lblProgramFees.setText(dto != null ? String.valueOf(dto.getFee()) : "Unknown");
    }

    @FXML
    void onClickTable(MouseEvent event) {
        RegistrationTm selected = tblEnrollment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtReId.setText(selected.getRegistrationId());
            datePicker.setValue(selected.getRegistrationDate());
            lblProgramFees.setText(String.valueOf(selected.getProgrammeFees()));
            cmbPatient.setValue(selected.getPatientId());
            cmbProgram.setValue(selected.getProgrammeId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void loadComboData() throws Exception {
        cmbPatient.setItems(FXCollections.observableArrayList(
                patientBO.getPatients().stream().map(PatientDTO::getPatientId).toList()
        ));
        cmbProgram.setItems(FXCollections.observableArrayList(
                therapyProgramBO.getPrograms().stream().map(TherapyProgramDTO::getProgramId).toList()
        ));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TranslateTransition slider = new TranslateTransition();
        slider.setNode(registrationAnchorpane);
        slider.setDuration(Duration.seconds(1.0));
        slider.setFromX(-200);
        slider.setToX(0);
        slider.play();


        setCellValues();
        try {
            loadComboData();
            refreshPage();
            setNextId();

        }catch (Exception e){

        }
    }

    private void setNextId() throws SQLException, IOException {
        String nextId = registrationBo.getNextId();
        txtReId.setText(nextId);
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


    private void refreshPage() throws Exception {
        loadTable();
        setNextId();
        clearFields();
    }

    private void clearFields() {
        txtReId.clear();
        datePicker.getValue();
        lblProgramFees.setText("");
        lblProgramName.setText("");
        lblPatientName.setText("");
        cmbPatient.getValue();
        cmbProgram.getValue();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }


    private void loadTable(){
        List<RegistrationDto> list = registrationBo.getAll();
        ObservableList<RegistrationTm> tmList = FXCollections.observableArrayList();
        for (RegistrationDto registrationDto : list) {
            tmList.add(new RegistrationTm(
                    registrationDto.getRegistrationId(),
                    registrationDto.getRegistrationDate(),
                    registrationDto.getProgrammeFees(),
                    registrationDto.getProgrammeId(),
                    registrationDto.getPatientId()
            ));
        }
        tblEnrollment.setItems(tmList);
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colProgrammeFees.setCellValueFactory(new PropertyValueFactory<>("programmeFees"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgrammeId.setCellValueFactory(new PropertyValueFactory<>("programmeId")); // <- add this

    }

}
