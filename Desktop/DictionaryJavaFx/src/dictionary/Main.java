package dictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent rootUi = FXMLLoader.load(getClass().getResource("rootUi.fxml"));
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(rootUi,1000,700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
