package dictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import sqlConnection.sqlConnect;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerRootUi<connection, pubic> implements Initializable {

    List<String> word = new ArrayList<>();// list of word
    sqlConnect sC = new sqlConnect();//connect to sql
    Connection connection = sC.getConnection();

    @FXML
    public Button btn_addButton;

    /**
     * Change Add Scene Function
     * @param actionEvent onclick btn_addButton
     * @throws IOException
     */
    public void changeAddScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent addUI = FXMLLoader.load(getClass().getResource("addUi.fxml"));
        Scene addScene = new Scene(addUI,1000,700);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();
    }

    @FXML
    public Button btn_searchButton;

    @FXML
    public Button btn_deleteButton;

    @FXML
    public Button btn_speakButton;

    @FXML
    public Button btn_updateButton;

    /**
     * update Word Function
     * @param actionEvent onclick btn_updateButton
     */
    public void Update(ActionEvent actionEvent) {
        String updateWord = tf_inputWord.getText();//get input
        if (updateWord != null && !updateWord.equals("")) {//check input

            // design updateFrame
            Dialog updateFrame = new Dialog();
            updateFrame.setTitle("Update Dictionary");
            updateFrame.setHeaderText("Update word: " + tf_inputWord.getText());

            updateFrame.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 15, 10, 10));

            TextArea ta_newMeaning = new TextArea();
            ta_newMeaning.setPromptText("Enter the new meaning");

            gridPane.add(new Label("Meaning"), 0, 1);
            gridPane.add(ta_newMeaning, 1, 1);

            updateFrame.getDialogPane().setContent(gridPane);

            /**
             * event updateFrame
             * TextArea -> enter new meaning
             * button OK -> update
             * button Cancel -> Cancel
             */
            Optional<ButtonType> result = updateFrame.showAndWait();
            if (result.get() == ButtonType.OK) {
                String newMeaning = ta_newMeaning.getText();//get input form TextArea of updateFrame
                if (newMeaning != null && !newMeaning.equals("")) {//check input
                    String sql = "UPDATE word SET detail = '" + newMeaning + "' WHERE title = '" + updateWord + "'";
                    try {
                        PreparedStatement pre = connection.prepareStatement(sql);
                        int check = pre.executeUpdate();
                        if (check > 0) {   //Notification of successful operation.
                            JOptionPane.showMessageDialog(null, "Success");//confirmation success
                        }
                        else { //The word was not found in the dictionary
                            /**
                             * Confirmation form to add word
                             * Button ok -> add word
                             * Button Cancel -> close
                             */
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Add To Dictionary");
                            alert.setHeaderText("This word was not found in the dictionary");
                            alert.setContentText("Do You Want To Add This Word To Dictionary");
                            /**
                             * event of Confirmation form to add word
                             */
                            Optional<ButtonType> addResult = alert.showAndWait();
                            if (addResult.get() == ButtonType.OK){//onclick Button OK => add word to dictionary
                                try {
                                    sql = "INSERT INTO `word` (`title`, `detail`) VALUES ('" + updateWord + "','" + newMeaning + "')";
                                    PreparedStatement pre2 = connection.prepareStatement(sql);
                                    check = pre2.executeUpdate();
                                    if (check != -1) {
                                        //Notification of successful operation.
                                        JOptionPane.showMessageDialog(null, "Success");
                                        showAllWords();//reload listview
                                    }
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {//the new meaning not found ->Cancel the operation
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("The operation was canceled because the new meaning was not found");
                    alert.setTitle("Input was not found");
                    alert.showAndWait();
                }
            }
        }
        else{// word to update not found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You haven't entered the word to update");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    @FXML
    public ListView lv_listOfWords;

    @FXML
    public TextField tf_inputWord;

    @FXML
    public TextArea ta_wordMeaning;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       showAllWords();//load listview
    }


}
