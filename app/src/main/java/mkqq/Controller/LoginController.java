package mkqq.Controller;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mkqq.BLL.UserBLL;
import mkqq.MainApp;

public class LoginController {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private UserBLL userBLL = new UserBLL();
    @FXML
    public void handleLogin(ActionEvent event){
        String name = userNameField.getText();
        String password = passwordField.getText();

        boolean loginSuccess = userBLL.checkLogin(name, password);

        if(loginSuccess) {

           switchscene(event);
        } else {
            Alert al2 = new Alert(Alert.AlertType.INFORMATION,"Đăng nhập thất bại ");
            al2.showAndWait();
        }
    }
    private void switchscene(ActionEvent event){
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("home_view.fxml"));
        try {
            root = fxmlLoader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
