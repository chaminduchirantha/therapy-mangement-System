package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.BOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.UserBO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dto.UserDTO;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class LoginController {

    UserBO userBO = (UserBO) BOFactory.getInstance().getBo(BOFactory.boType.USER);


    @FXML
    private AnchorPane LoginAnchorPane;

    @FXML
    private Button loginBut;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Pane userNamePasswordAnchorePane;

    private final Pattern passwordPattern = Pattern.compile("^.{8,}$");


    static String liveUserRole = "";
    static String liveUserId;

    @FXML
    void eyeHiddenPassword(MouseEvent event) {

    }

    @FXML
    void loginOnAction(ActionEvent event) {
        String username =txtName.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username and password are required!").show();
//            showAlert("");
            return;
        }

        if (!passwordPattern.matcher(password).matches()) {
            new Alert(Alert.AlertType.ERROR, "Password must be at least 8 characters long!").show();
            return;
        }


        List<UserDTO> userList = userBO.getUsers();

        boolean found = false;

        for (UserDTO user : userList) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                found = true;
                if (user.getRole().equalsIgnoreCase("Admin")) {
                    MainLayoutController.isAdmin = true;
                } else {
                    MainLayoutController.isAdmin = false;
                }
                loadMainLayout();
                break;
            }
        }

        if (!found) {
            new Alert(Alert.AlertType.ERROR, "invalid password...!").show();
        }
    }

    private void loadMainLayout() {
        try {
            Stage stage = (Stage) LoginAnchorPane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"))));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void showAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Login Failed");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }

}
