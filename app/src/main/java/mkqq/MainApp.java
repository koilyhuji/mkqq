/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package mkqq;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mkqq.Controller.HomeController;
import mkqq.DAO.UserDAO;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login_view.fxml"));

        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
        
    }

}