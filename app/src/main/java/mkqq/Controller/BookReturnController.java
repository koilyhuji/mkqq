package mkqq.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import mkqq.BLL.BookReturnBLL;
import mkqq.BLL.MemberBLL;
import mkqq.DTO.BookDTO;
import mkqq.DTO.BookIssueDTO;
import mkqq.DTO.BookReturnDTO;
import mkqq.DTO.MemberDTO;
import mkqq.MainApp;

public class BookReturnController {

    @FXML
    public DatePicker txt_issu_date;
    @FXML
    public TextField txt_fine;
    @FXML
    public DatePicker txt_rt_date;
    @FXML
    public TableView<BookReturnDTO> rt_tbl;
    @FXML
    public ComboBox cmb_issue_id;

    public BookReturnBLL bookReturnBLL = new BookReturnBLL();
    List<BookReturnDTO> bookReturnList = bookReturnBLL.getBookReturnDTOS();
    List<BookIssueDTO> issueDTOList = new BookIssueBLL().getBookIssueDTOS();
    List<BookDTO> bookDTOList = new BookBLL().getBookDTOS();

    public void initialize() throws ClassNotFoundException {

        rt_tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        rt_tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        rt_tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        rt_tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("fine"));

        ObservableList<BookReturnDTO> returns = rt_tbl.getItems();
        returns.addAll(bookReturnList);
        rt_tbl.setItems(returns);

        cmb_issue_id.getItems().clear();
        ObservableList cmbIssue = cmb_issue_id.getItems();
        for (var issuedto: issueDTOList
             ) {
            cmbIssue.add(issuedto.getIssueId());
        };
        cmb_issue_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (cmb_issue_id.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                try {

                    String issueId = (String) cmb_issue_id.getSelectionModel().getSelectedItem();
                    BookIssueDTO isse = new BookIssueBLL().getBookIssueFromId(issueId);
                    txt_issu_date.setValue(isse.getDate().toLocalDate());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        txt_rt_date.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

                if (txt_rt_date.getValue() == null) {
                    return;
                }

                LocalDate returned = txt_rt_date.getValue();
                LocalDate issued = txt_issu_date.getValue();

                Date date1 = Date.valueOf(issued);
                Date date2 = Date.valueOf(returned);

                long diff = date2.getTime() - date1.getTime();

                System.out.println(TimeUnit.MILLISECONDS.toDays(diff));
                int dateCount = (int) TimeUnit.MILLISECONDS.toDays(diff);
                float fine = 0;

                if (dateCount > 14) {
                    fine = dateCount * 15;
                }
                txt_fine.setText(Float.toString(fine));
            }
        });
    }
    @FXML
    public void btn_new(ActionEvent actionEvent) {
        txt_fine.clear();
        txt_issu_date.setPromptText("Ngày mượn");
        cmb_issue_id.getSelectionModel().clearSelection();
        txt_rt_date.setPromptText("Ngày Trả");
    }

    @FXML
    public void btn_add_inveb(ActionEvent actionEvent) {
        if (cmb_issue_id.getSelectionModel().isEmpty() ||
                txt_issu_date.getValue() == null ||
                txt_rt_date.getValue() == null ||
                txt_fine.getText().isEmpty()
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "điền đủ thông tin ",
                    ButtonType.OK);
            alert.showAndWait();

            return;
        }

        String issueID = (String) cmb_issue_id.getSelectionModel().getSelectedItem();
        BookReturnDTO br = new BookReturnDTO(Date.valueOf(txt_issu_date.getValue()),Date.valueOf(txt_rt_date.getValue()),txt_fine.getText(),issueID);
        boolean kq = bookReturnBLL.insertBookReturn(br);
        if (kq) {
            System.out.println("successful");

            String bookId = null;
            bookId = new BookIssueBLL().getBookIssueFromId(issueID).getBookId();

            BookDTO booktoupdate = new BookBLL().getBookFromId(bookId);
            booktoupdate.setStatus("Available");
            boolean kqupdate = new BookBLL().updateBook(booktoupdate);

            if (kqupdate) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Update trạng thái sách.",
                        ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            System.out.println("Something went wrong");
        }
        rt_tbl.getItems().clear();
        refresh();
        cmb_issue_id.getItems().remove(issueID);
        cmb_issue_id.getSelectionModel().clearSelection();
        txt_issu_date.getEditor().clear();
        txt_fine.clear();
        txt_rt_date.getEditor().clear();
    }
    @FXML
    public void img_back(MouseEvent event) throws IOException {
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

    public void refresh(){
        rt_tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        rt_tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        rt_tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        rt_tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("fine"));

        ObservableList<BookReturnDTO> returns = rt_tbl.getItems();
        bookReturnList.clear();
        bookReturnList = new BookReturnBLL().getBookReturnDTOS();
        returns.addAll(bookReturnList);
        rt_tbl.setItems(returns);

        cmb_issue_id.getItems().clear();
        ObservableList cmbIssue = cmb_issue_id.getItems();
        for (var issuedto: issueDTOList
        ) {
            cmbIssue.add(issuedto.getIssueId());
        };
        cmb_issue_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (cmb_issue_id.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                try {

                    String issueId = (String) cmb_issue_id.getSelectionModel().getSelectedItem();
                    BookIssueDTO isse = new BookIssueBLL().getBookIssueFromId(issueId);
                    txt_issu_date.setValue(isse.getDate().toLocalDate());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        txt_rt_date.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

                if (txt_rt_date.getValue() == null) {
                    return;
                }

                LocalDate returned = txt_rt_date.getValue();
                LocalDate issued = txt_issu_date.getValue();

                Date date1 = Date.valueOf(issued);
                Date date2 = Date.valueOf(returned);

                long diff = date2.getTime() - date1.getTime();

                System.out.println(TimeUnit.MILLISECONDS.toDays(diff));
                int dateCount = (int) TimeUnit.MILLISECONDS.toDays(diff);
                float fine = 0;

                if (dateCount > 14) {
                    fine = dateCount * 15;
                }
                txt_fine.setText(Float.toString(fine));
            }
        });
    }

}
