package mkqq.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
        tbl_bk.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookDTO>() {
            @Override
            public void changed(ObservableValue<? extends BookDTO> observable, BookDTO oldValue, BookDTO newValue) {
                if (newValue != null){
                    loadIssueData(newValue.getId());
                    loadReturnData(newValue.getId());
                }
            }
        });


    }

    private void searchBooks(String newValue) {
        String query = newValue.toLowerCase();
        List<BookDTO> filteredList = new ArrayList<>();
        for (var book : bookList
             ) {
            if(book.getAuthor().toLowerCase().contains(query) || book.getTitle().toLowerCase().contains(query) || book.getId().toLowerCase().contains(query)){
                filteredList.add(book);
            }
        }
        System.out.println("--------------");
        loadBookData(filteredList);
        if(!filteredList.isEmpty()){

            loadIssueData(filteredList.get(0).getId());
            loadReturnData(filteredList.get(0).getId());
        }else {
            ObservableList<BookIssueDTO> bookIssueDTOS = tbl_issue.getItems();
            bookIssueDTOS.clear();
            tbl_issue.setItems(bookIssueDTOS);

            ObservableList<BookReturnDTO> bookReturnDTOS = tbl_return.getItems();
            bookReturnDTOS.clear();
            tbl_return.setItems(bookReturnDTOS);
        }
    }

    private void setUpReturnTable() {
        tbl_return.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_return.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        tbl_return.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("fine"));
        tbl_return.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
    }

    private void setUpIssueTable() {
        tbl_issue.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tbl_issue.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tbl_issue.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tbl_issue.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setUpBookTable() {
        tbl_bk.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_bk.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tbl_bk.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tbl_bk.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
        loadBookData(bookList);
    }
    private void loadBookData(List<BookDTO> bookList) {
        ObservableList<BookDTO> books = tbl_bk.getItems();
        books.clear();
        books.addAll(bookList);
        tbl_bk.setItems(books);
    }
    private void loadIssueData(String bookId) {
        List<BookIssueDTO> issueList = bookIssueBLL.getIssuesByBookId(bookId);
        ObservableList<BookIssueDTO> issues = tbl_issue.getItems();
        issues.clear();
        issues.addAll(issueList);
        tbl_issue.setItems(issues);
    }
    private void loadReturnData(String bookId) {
        List<BookReturnDTO> returnList = bookReturnBLL.getReturnsByBookId(bookId);
        ObservableList<BookReturnDTO> returns = tbl_return.getItems();
        returns.clear();
        returns.addAll(returnList);
        tbl_return.setItems(returns);
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
    public void onlineSearchView(MouseEvent event){
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("online_search_view.fxml"));
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
