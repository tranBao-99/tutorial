package dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sqlConnection.sqlConnect;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerAddUi {

    sqlConnect sC = new sqlConnect();
    Connection connection = sC.getConnection();

    @FXML
    public Button btn_add;

    @FXML
    public Button btn_backRootButton;

    /**
     * Return root Scene Function
     * @param actionEvent onclick btn_backButton
     * @throws IOException
     */
    public void changeRootScene(ActionEvent actionEvent) throws IOException {
        Parent rootUI = FXMLLoader.load(getClass().getResource("rootUi.fxml"));
        Scene rootScene = new Scene(rootUI,1000,700);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
    }


    @FXML
    public TextField tf_inputWordAdd;

    @FXML
    public TextArea ta_meaningWordAdd;

}
