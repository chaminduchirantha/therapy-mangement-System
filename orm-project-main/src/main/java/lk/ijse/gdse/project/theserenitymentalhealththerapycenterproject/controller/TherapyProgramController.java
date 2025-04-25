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
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapistBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapistDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapyProgramDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm.TherapyProgramTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class TherapyProgramController implements Initializable {

    TherapyProgramBO therapyProgramBO = BOFactory.getInstance().getBO(BOTypes.PROGRAM);
    TherapistBO therapistBO = BOFactory.getInstance().getBO(BOTypes.THERAPIST);

    @FXML
    private Button btnDelete, btnReset, btnSave, btnUpdate;

    @FXML
    private ComboBox<String> cmbTherapist;

    @FXML
    private AnchorPane programmeAnchorpane;

    @FXML
    private TableColumn<TherapyProgramTM, Integer> colDuration;

    @FXML
    private TableColumn<TherapyProgramTM, Double> colFee;

    @FXML
    private TableColumn<TherapyProgramTM, String> colId, colName, colTherapist;

    @FXML
    private Label lblId, lblTherapistName;

    @FXML
    private TableView<TherapyProgramTM> tblProgram;

    @FXML
    private TextField txtDuration, txtFee, txtName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TranslateTransition slider = new TranslateTransition();
        slider.setNode(programmeAnchorpane);
        slider.setDuration(Duration.seconds(1.0));
        slider.setFromX(-200);
        slider.setToX(0);
        slider.play();


        setCellValues();
        try {
            refreshPage();
        } catch (Exception e) {
            showErrorAlert("1"+e.getMessage());
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String id = lblId.getText();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this programme?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                boolean isDeleted = therapyProgramBO.deleteProgram(id);
                if (isDeleted) {
                    showSuccessAlert("Programme deleted successfully.");
                    refreshPage();
                } else {
                    showErrorAlert("Failed to delete programme.");
                }
            }
        } catch (Exception e) {
            showErrorAlert("2"+e.getMessage());
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
//        try {
            TherapyProgramDTO dto = new TherapyProgramDTO(
                    lblId.getText(),
                    txtName.getText(),
                    Integer.parseInt(txtDuration.getText()),
                    Double.parseDouble(txtFee.getText()),
                    cmbTherapist.getValue()
            );
            boolean isSaved = therapyProgramBO.saveProgram(dto);
            if (isSaved) {
                showSuccessAlert("Programme saved successfully.");
                refreshPage();
            } else {
                showErrorAlert("Failed to save programme.");
            }
//        } catch (Exception e) {
//            showErrorAlert(e.getMessage());
//        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            TherapyProgramDTO dto = new TherapyProgramDTO(
                    lblId.getText(),
                    txtName.getText(),
                    Integer.parseInt(txtDuration.getText()),
                    Double.parseDouble(txtFee.getText()),
                    cmbTherapist.getValue()
            );
            boolean isUpdated = therapyProgramBO.updateProgram(dto);
            if (isUpdated) {
                showSuccessAlert("Programme updated successfully.");
                refreshPage();
            } else {
                showErrorAlert("Failed to update programme.");
            }
        } catch (Exception e) {
            showErrorAlert("3"+e.getMessage());
        }
    }

    @FXML
    void cmbTherapistOnAction(ActionEvent event) {
        if (cmbTherapist.getValue() == null) return;

        String selectedID = cmbTherapist.getValue();
        TherapistDTO therapistDTO = therapistBO.getTherapistById(selectedID);
        if (therapistDTO != null) {
            lblTherapistName.setText(therapistDTO.getName());
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TherapyProgramTM selected = tblProgram.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(selected.getProgramId());
            txtName.setText(selected.getName());
            txtDuration.setText(String.valueOf(selected.getDurationInWeeks()));
            txtFee.setText(String.valueOf(selected.getFee()));
            cmbTherapist.setValue(selected.getTherapistId());

            TherapistDTO therapistDTO = therapistBO.getTherapistById(selected.getTherapistId());
            if (therapistDTO != null) {
                lblTherapistName.setText(therapistDTO.getName());
            }

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationInWeeks"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
    }

    private void refreshPage() throws Exception {
        loadTable();
        setNextId();
        clearFields();
        loadProgrammeIds();
    }

    private void loadTable() {
        try {
            List<TherapyProgramDTO> programDTOS = therapyProgramBO.getPrograms();
            ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();

            for (TherapyProgramDTO dto : programDTOS) {
                therapyProgramTMS.add(new TherapyProgramTM(
                        dto.getProgramId(),
                        dto.getName(),
                        dto.getDurationInWeeks(),
                        dto.getFee(),
                        dto.getTherapistId()
                ));
            }
            tblProgram.setItems(therapyProgramTMS);
        } catch (Exception e) {
            showErrorAlert("4"+e.getMessage());
        }
    }

    private void setNextId() {
        String nextId = therapyProgramBO.getNextProgramId();
        lblId.setText(nextId);
    }

    private void clearFields() {
        txtName.clear();
        txtDuration.clear();
        txtFee.clear();
        cmbTherapist.getValue();
        lblTherapistName.setText("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void loadProgrammeIds() throws Exception {
        List<TherapistDTO> therapists = therapistBO.getTherapists();
        cmbTherapist.getItems().clear();
        for (TherapistDTO dto : therapists) {
            cmbTherapist.getItems().add(dto.getTherapistId());
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Completed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Operation Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
