package mkqq.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController {
    public AnchorPane root;

    public void navigate(MouseEvent event) throws IOException {
        System.out.println("yooo");
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            switch (icon.getId()) {
                case "ic_member":
                    root = FXMLLoader.load(this.getClass().getResource("member_view.fxml"));
                    break;
                case "books":

                    break;
                case "issue":

                    break;
                case "bk_return":

                    break;
                case "bk_search":

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
}
