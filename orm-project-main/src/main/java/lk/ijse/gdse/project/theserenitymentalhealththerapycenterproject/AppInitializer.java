package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image image = new Image(getClass().getResourceAsStream("/images/logodesign.png"));
        stage.getIcons().add(image);


        stage.setTitle("Serenity Mental Therapy Center");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}