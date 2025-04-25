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
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.BOTypes;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.PatientBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapistBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapySessionBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.*;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm.TherapySessionTM;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {

    TherapySessionBO sessionBO = BOFactory.getInstance().getBO(BOTypes.SESSION);
    PatientBO patientBO = BOFactory.getInstance().getBO(BOTypes.PATIENT);
    TherapistBO therapistBO = BOFactory.getInstance().getBO(BOTypes.THERAPIST);
    TherapyProgramBO programBO = BOFactory.getInstance().getBO(BOTypes.PROGRAM);

    @FXML private Button btnDelete, btnReset, btnSave, btnUpdate;
    @FXML private ComboBox<String> cmbPatient, cmbTherapist, cmbProgram;
    @FXML private DatePicker datePicker;
    @FXML
    private AnchorPane sessionAnchorpane;
    @FXML private Label lblId, lblPatientName, lblTherapistName, lblProgramName;
    @FXML private TableView<TherapySessionTM> tblSession;
    @FXML private TableColumn<TherapySessionTM, String> colId, colPatient, colTherapist, colProgram;
    @FXML private TableColumn<TherapySessionTM, Date> colDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TranslateTransition slider = new TranslateTransition();
        slider.setNode(sessionAnchorpane);
        slider.setDuration(Duration.seconds(1.0));
        slider.setFromX(-200);
        slider.setToX(0);
        slider.play();

        setCellValues();
        try {
            refreshPage();
        } catch (Exception e) {
            showErrorAlert("Initialization error: " + e.getMessage());
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this session?", ButtonType.YES, ButtonType.NO);
            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                if (sessionBO.deleteSession(lblId.getText())) {
                    showSuccessAlert("Session deleted.");
                    refreshPage();
                } else {
                    showErrorAlert("Delete failed.");
                }
            }
        } catch (Exception e) {
            showErrorAlert("Delete error: " + e.getMessage());
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        TherapySessionDTO dto = new TherapySessionDTO(
                lblId.getText(),
                datePicker.getValue(),
                cmbPatient.getValue(),
                cmbProgram.getValue(),
                cmbTherapist.getValue()
        );
        if (sessionBO.saveSession(dto)) {
            showSuccessAlert("Session saved.");
            refreshPage();
        } else {
            showErrorAlert("Save failed.");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            TherapySessionDTO dto = new TherapySessionDTO(
                    lblId.getText(),
                    datePicker.getValue(),
                    cmbPatient.getValue(),
                    cmbProgram.getValue(),
                    cmbTherapist.getValue()
            );
            if (sessionBO.updateSession(dto)) {
                showSuccessAlert("Session updated.");
                refreshPage();
            } else {
                showErrorAlert("Update failed.");
            }
        } catch (Exception e) {
            showErrorAlert("Update error: " + e.getMessage());
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

        TherapyProgramDTO dto = programBO.getProgramById(cmbProgram.getValue());
        lblProgramName.setText(dto != null ? dto.getName() : "Unknown");
    }

    @FXML
    void cmbTherapistOnAction(ActionEvent event) {
        if (cmbTherapist.getValue() == null) return;

        TherapistDTO dto = therapistBO.getTherapistById(cmbTherapist.getValue());
        System.out.println(dto);
        lblTherapistName.setText(dto != null ? dto.getName() : "Unknown");
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TherapySessionTM selected = tblSession.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(selected.getSessionId());
            cmbPatient.setValue(selected.getPatId());
            cmbProgram.setValue(selected.getProgrammeId());
            cmbTherapist.setValue(selected.getTherapistId());
            datePicker.setValue(selected.getDate());

            cmbPatientOnAction(null);
            cmbProgramOnAction(null);
            cmbTherapistOnAction(null);

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programmeId"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void refreshPage() throws Exception {
        loadTable();
        loadComboData();
        clearFields();
        lblId.setText(sessionBO.getNextSessionId());
    }

    private void loadTable() {
        List<TherapySessionDTO> list = sessionBO.getSessions();
        ObservableList<TherapySessionTM> tmList = FXCollections.observableArrayList();
        for (TherapySessionDTO dto : list) {
            tmList.add(new TherapySessionTM(
                    dto.getSessionId(),
                    dto.getDate(),
                    dto.getPatId(),
                    dto.getTherapistId(),
                    dto.getProgrammeId()
            ));
        }
        tblSession.setItems(tmList);
    }

    private void loadComboData() throws Exception {
        cmbPatient.setItems(FXCollections.observableArrayList(
                patientBO.getPatients().stream().map(PatientDTO::getPatientId).toList()
        ));
        cmbProgram.setItems(FXCollections.observableArrayList(
                programBO.getPrograms().stream().map(TherapyProgramDTO::getProgramId).toList()
        ));
        cmbTherapist.setItems(FXCollections.observableArrayList(
                therapistBO.getTherapists().stream().map(TherapistDTO::getTherapistId).toList()
        ));
    }

    private void clearFields() {
        cmbPatient.setValue(null);
        cmbProgram.setValue(null);
        cmbTherapist.setValue(null);
        datePicker.setValue(null);
        lblPatientName.setText("");
        lblProgramName.setText("");
        lblTherapistName.setText("");
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
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

    public void cmbTherapistOnAcc(ActionEvent actionEvent) {
        // Empty method for possible future extension
    }
}
