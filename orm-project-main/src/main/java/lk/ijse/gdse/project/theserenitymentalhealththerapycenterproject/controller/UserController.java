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
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.UserBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.TherapistDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.UserDTO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm.TherapistTM;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.tm.UserTM;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);


    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TableColumn<UserTM, String> colId;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private AnchorPane userAnchorapne;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPasswordUser;

    @FXML
    private TextField txtRole;


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        try {
            String id = lblId.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this User?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                boolean isDeleted = userBO.deleteUser(id);
                if (isDeleted) {
                    showSuccessAlert("User deleted successfully.");
                    refreshPage();
                } else {
                    showErrorAlert("Failed to delete user.");
                }
            }

        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        try {
            UserDTO dto = new UserDTO(
                    lblId.getText(),
                    txtName.getText(),
                    txtPasswordUser.getText(),
                    txtRole.getText()
            );

            boolean isSaved = userBO.saveUser(dto);
            if (isSaved) {
                showSuccessAlert("User saved successfully.");
                refreshPage();
            } else {
                showErrorAlert("Failed to save user .");
            }

        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            UserDTO dto = new UserDTO(
                    lblId.getText(),
                    txtName.getText(),
                    txtPasswordUser.getText(),
                    txtRole.getText()
            );

            boolean isSaved = userBO.updateUser(dto);
            if (isSaved) {
                showSuccessAlert("User update successfully.");
                refreshPage();
            } else {
                showErrorAlert("Failed to update user .");
            }

        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        UserTM selected = tblUser.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(selected.getUserId());
            txtName.setText(selected.getUserName());
            txtPasswordUser.setText(selected.getPassword());
            txtRole.setText(selected.getRole());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TranslateTransition slider = new TranslateTransition();
        slider.setNode(userAnchorapne);
        slider.setDuration(Duration.seconds(1.0));
        slider.setFromX(-200);
        slider.setToX(0);
        slider.play();
        setCellValues();

        try {
            refreshPage();
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void setCellValues() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void refreshPage() {
        loadTable();
        setNextId();
        clearFields();
    }

    private void loadTable() {
        try {
            List<UserDTO> userDTOS = userBO.getUsers();
            ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

            for (UserDTO userDTO : userDTOS) {
                UserTM userTM = new UserTM(
                        userDTO.getUserId(),
                        userDTO.getUserName(),
                        userDTO.getPassword(),
                        userDTO.getRole()
                );
                userTMS.add(userTM);
            }
            tblUser.setItems(userTMS);
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void setNextId() {
        String nextId = userBO.getNextUserId();
        lblId.setText(nextId);
    }

    private void clearFields() {
        txtRole.clear();
        txtPasswordUser.clear();
        txtName.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
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
