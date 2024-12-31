package mkqq.Controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mkqq.DTO.BookDTO;

public class BookPreviewController {
    @FXML
    public TableView<BookDTO> tbl_preview;
    @FXML
    public TableColumn<BookDTO,String> col_id;
    @FXML
    public TableColumn<BookDTO,String> col_title;
    @FXML
    public TableColumn<BookDTO,String> col_author;
    @FXML
    public TableColumn<BookDTO,String> col_status;

    private List<BookDTO> bookData;
    private boolean accepted = false;

    public void setData(List<BookDTO> bookData) {
        this.bookData = bookData;
        initTable();
    }

    public void initTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<BookDTO> observableList = FXCollections.observableArrayList(bookData);
        tbl_preview.setItems(observableList);
    }

    @FXML
    public void btn_accept(ActionEvent event) {
        accepted = true;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void btn_cancel(ActionEvent event) {
        accepted = false;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public boolean isAccepted() {
        return accepted;
    }

    public List<BookDTO> getBookData() {
        return bookData;
    }
}
