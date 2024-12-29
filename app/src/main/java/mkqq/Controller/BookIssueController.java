package mkqq.Controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import mkqq.BLL.BookBLL;
import mkqq.BLL.BookIssueBLL;
import mkqq.BLL.MemberBLL;
import mkqq.DTO.BookDTO;
import mkqq.DTO.BookIssueDTO;
import mkqq.DTO.MemberDTO;
import mkqq.MainApp;
import mkqq.utils.IssueViewModel;

public class BookIssueController {

    @FXML
    public TextField txt_issid;
    @FXML
    public DatePicker txt_isu_date;
    @FXML
    public TextField txt_name;
    @FXML
    public TextField txt_title;
    @FXML
    public ComboBox mem_is_id;
    @FXML
    public ComboBox book_id;
    @FXML
    public TableView<IssueViewModel> bk_ssue_tbl;
    @FXML
    public AnchorPane bk_iss;

    public BookIssueBLL bookIssueBLL = new BookIssueBLL();
    List<BookIssueDTO> bookIssueList = bookIssueBLL.getBookIssueDTOS();
    List<MemberDTO> memberDTOList = new MemberBLL().getMemberDTOS();
    List<BookDTO> bookDTOList = new BookBLL().getBookDTOS();

    public void initialize()  {


        bk_ssue_tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        bk_ssue_tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        bk_ssue_tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberName"));
        bk_ssue_tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("bookName"));
        ObservableList<IssueViewModel> detailIssueList = FXCollections.observableArrayList();
        //fucking digusting
        for (BookIssueDTO bookIssue: bookIssueList
             ) {
            MemberDTO member = new MemberBLL().getMemberfromID(bookIssue.getMemberId());
            BookDTO book = new BookBLL().getBookFromId(bookIssue.getBookId());
            IssueViewModel vm = new IssueViewModel(bookIssue.getIssueId(),bookIssue.getDate(),book, member,book.getTitle(),member.getName());
            detailIssueList.add(vm);
        }

        ObservableList<IssueViewModel> issue = FXCollections.observableArrayList(detailIssueList);
        bk_ssue_tbl.setItems(issue);
        bk_ssue_tbl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IssueViewModel>() {
            @Override
            public void changed(ObservableValue<? extends IssueViewModel> observable, IssueViewModel oldValue, IssueViewModel newValue) {
                IssueViewModel selectedItem = bk_ssue_tbl.getSelectionModel().getSelectedItem();
                try{
                    if(selectedItem != null){
                        txt_isu_date.setValue(selectedItem.getDate().toLocalDate());
                        mem_is_id.setValue(selectedItem.getMember().getId());
                        book_id.setValue(selectedItem.getBook().getId());
                    }
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

        mem_is_id.getItems().clear();

        ObservableList cmbmembers = mem_is_id.getItems();
        for (MemberDTO member: memberDTOList
             ) {
            cmbmembers.add(member.getId());
        };

        book_id.getItems().clear();
        ObservableList cmbbooks = book_id.getItems();

        for(BookDTO book : bookDTOList){
            if(book.getStatus().equals("Available")){
                cmbbooks.add(book.getId());

            }
        }
        mem_is_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if (mem_is_id.getSelectionModel().getSelectedItem() != null) {
                    Object selectedItem = mem_is_id.getSelectionModel().getSelectedItem();
                    if (selectedItem.equals(null) || mem_is_id.getSelectionModel().isEmpty()) {
                        return;
                    }
                    try {
                        String memberId = selectedItem.toString();
                        MemberDTO member = new MemberBLL().getMemberfromID(memberId);

                        txt_name.setText(member.getName());

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ;

        book_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (book_id.getSelectionModel().getSelectedItem() != null) {
                    Object selectedItem = book_id.getSelectionModel().getSelectedItem();

                    try {
                        txt_title.clear();
                        BookDTO book = new BookBLL().getBookFromId(selectedItem.toString());
                        if(book.getStatus().equals("Available")){
                            txt_title.setText(book.getTitle());
                        }
                        else {
                            book_id.getEditor().clear();
                            Alert alert = new Alert(Alert.AlertType.ERROR,
                                    "Sách này không mượn được!",
                                    ButtonType.OK);
                             alert.showAndWait();
                        }


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });;
    }

    @FXML
    public void new_action(ActionEvent actionEvent)  {
        txt_title.clear();
        txt_name.clear();
        mem_is_id.getSelectionModel().clearSelection();
        mem_is_id.setPromptText("Member Id");
        book_id.getSelectionModel().clearSelection();
        book_id.setPromptText("Book Id ");
        txt_isu_date.setPromptText("Ngày mượn");

        String id = bookIssueBLL.getNewId();
        txt_issid.setText(id);
    }

    @FXML
    public void add_Action(ActionEvent actionEvent) {

        if (txt_issid.getText().isEmpty() ||
                book_id.getSelectionModel().getSelectedItem() == null ||
                mem_is_id.getSelectionModel().getSelectedItem() == null
                || txt_isu_date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Vui lòng điền đủ thông tin.",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }else {
            String memberId = (String) mem_is_id.getSelectionModel().getSelectedItem();
            String bookId = (String) book_id.getSelectionModel().getSelectedItem();
            BookIssueDTO newBookIssue = new BookIssueDTO(txt_issid.getText(), Date.valueOf(txt_isu_date.getValue().toString()), memberId, bookId);
            BookDTO booktoUpdate = new BookBLL().getBookFromId(bookId);
            booktoUpdate.setStatus("Unavailable");
            try {
                boolean check  = bookIssueBLL.insertBookIssue(newBookIssue);
                boolean bookcheck = new BookBLL().updateBook(booktoUpdate);
                if (check) {
                    System.out.println("Data insertion successfull");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "Status updated.",
                            ButtonType.OK);
                    alert.showAndWait();
                }
                else {
                    System.out.println("ERROR");
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        txt_title.clear();
        txt_name.clear();
        txt_issid.clear();
        txt_isu_date.setPromptText("Ngày mượn");
        mem_is_id.getSelectionModel().clearSelection();
        book_id.getSelectionModel().clearSelection();
        refreshTable();

    }
    public void refreshTable(){

        bk_ssue_tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        bk_ssue_tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        bk_ssue_tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberName"));
        bk_ssue_tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("bookName"));

        bookIssueList.clear();
        bookIssueList = bookIssueBLL.getBookIssueDTOS();
        bookDTOList.clear();
        bookDTOList = new BookBLL().getBookDTOS();
        ObservableList<IssueViewModel> detailIssueList = FXCollections.observableArrayList();
        //fucking digusting
        for (BookIssueDTO bookIssue: bookIssueList
        ) {
            MemberDTO member = new MemberBLL().getMemberfromID(bookIssue.getMemberId());
            BookDTO book = new BookBLL().getBookFromId(bookIssue.getBookId());
            IssueViewModel vm = new IssueViewModel(bookIssue.getIssueId(),bookIssue.getDate(),book, member,book.getTitle(),member.getName());
            detailIssueList.add(vm);
        }

        ObservableList<IssueViewModel> issue = FXCollections.observableArrayList(detailIssueList);
        bk_ssue_tbl.setItems(issue);
        mem_is_id.setPromptText("Member Id");
        book_id.setPromptText("Book Id ");
        txt_isu_date.setPromptText("Ngày mượn");
        mem_is_id.getItems().clear();

        ObservableList cmbmembers = mem_is_id.getItems();
        for (MemberDTO member: memberDTOList
        ) {
            cmbmembers.add(member.getId());
        };

        book_id.getItems().clear();
        ObservableList cmbbooks = book_id.getItems();

        for(BookDTO book : bookDTOList){
            if(book.getStatus().equals("Available")){
                cmbbooks.add(book.getId());

            }
        }

    }

    @FXML
    public void delete_Action(ActionEvent actionEvent)  {

    }
    @FXML
    public void back_click(MouseEvent event) {
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
