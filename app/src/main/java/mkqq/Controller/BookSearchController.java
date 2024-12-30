package mkqq.Controller;

import java.io.IOException;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import mkqq.BLL.BookBLL;
import mkqq.BLL.BookIssueBLL;
import mkqq.BLL.BookReturnBLL;
import mkqq.DTO.BookDTO;
import mkqq.DTO.BookIssueDTO;
import mkqq.DTO.BookReturnDTO;
import mkqq.MainApp;

public class BookSearchController {

    @FXML
    private AnchorPane sch_root;
    @FXML
    private TextField bk_sch;
    @FXML
    private TableView<BookDTO> tbl_bk;

    @FXML
    private TableView<BookIssueDTO> tbl_issue;

    @FXML
    private TableView<BookReturnDTO> tbl_return;

    private BookBLL bookBLL = new BookBLL();
    private BookIssueBLL bookIssueBLL = new BookIssueBLL();
    private BookReturnBLL bookReturnBLL = new BookReturnBLL();

    private List<BookDTO> bookList = bookBLL.getBookDTOS();

    public void initialize() {
        setUpBookTable();
        setUpIssueTable();
        setUpReturnTable();

        bk_sch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchBooks(newValue);
            }
        });


    }

    private void searchBooks(String newValue) {
    }

    private void setUpReturnTable() {
    }

    private void setUpIssueTable() {
    }

    private void setUpBookTable() {
    }

    public void img_bk(MouseEvent event) throws IOException {
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

        TranslateTransition tt = new TranslateTransition(Duration.millis(350), scene.getRoot());
        tt.setFromX(-scene.getWidth());
        tt.setToX(0);
        tt.play();
    }
}
