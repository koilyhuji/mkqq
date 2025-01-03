package mkqq.Controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mkqq.BLL.BookBLL;
import mkqq.DTO.BookDTO;
import mkqq.MainApp;
import mkqq.utils.CommonUtils;

public class HomeController {
    public AnchorPane root;

    public void initialize(){

    }
    public void navigate(MouseEvent event) throws IOException {
        
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;
           
            switch (icon.getId()) {
                case "ic_member":
                    switchscene(event,"member_view.fxml");
                    break;
                case "ic_books":
                    switchscene(event, "books_view.fxml");
                    break;
                case "ic_issue":
                    switchscene(event,"bookissue_view.fxml");
                    break;
                case "ic_return":
                    switchscene(event,"bookreturn_view.fxml");
                    break;
                case "ic_search":
                    switchscene(event,"booksearch_view.fxml");
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();


            }
        }
    }

    private void switchscene(MouseEvent event, String resource){
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(resource));
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
