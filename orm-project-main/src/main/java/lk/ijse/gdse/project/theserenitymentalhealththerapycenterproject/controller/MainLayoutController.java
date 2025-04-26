package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {
    public static String role;
    public static boolean isAdmin;



    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnEnrollment;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnProgram;

    @FXML
    private Button btnSession;

    @FXML
    private Button btnTherapist;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnLogIut;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!isAdmin) {
            btnProgram.setDisable(true);
            btnUser.setDisable(true);
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {

    }

    @FXML
    void btnEnrollmentOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/Registration.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/PatientView.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/PaymentView.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnProgramOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/TherapyProgramView.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSessionOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/TherapySessionView.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnTherapistOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/TherapistView.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUserOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Log out this Programme?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> optionalButtonType = alert.showAndWait();

            if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
                boolean isDeleted = true;
                if (isDeleted) {
                    mainAnchorPane.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/LoginView.fxml"));
                    mainAnchorPane.getChildren().add(load);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Log out not Programme").show();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "not found").show();

        }

    }

}
