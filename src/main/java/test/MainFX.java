package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 639, 400);

            Image icon = new Image(getClass().getResourceAsStream("/Front/Blog/2.png"));
            primaryStage.getIcons().add(icon);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Adventure");

            //primaryStage.setResizable(false);

            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}