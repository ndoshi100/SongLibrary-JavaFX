package app;/**
 * Created by yugantjoshi on 2/6/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SongLib extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/SongLib_layout.fxml"));

        GridPane root = (GridPane) loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Song Library");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
