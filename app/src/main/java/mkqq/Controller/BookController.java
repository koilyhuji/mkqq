package mkqq.Controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import mkqq.MainApp;
import mkqq.BLL.BookBLL;
import mkqq.DTO.BookDTO;
import mkqq.utils.CommonUtils;

public class BookController {
    @FXML
    public TextField txt_bk_id;
    @FXML
    public TextField txt_bk_title;
    @FXML
    public TextField txt_bk_auth;
    @FXML
    public TextField txt_bk_st;
    @FXML
    public TableView<BookDTO> tbl_bk;
    @FXML
    public AnchorPane bk_root;
    @FXML
    public Button btn_add;
    public BookBLL bookBLL = new BookBLL();
    List<BookDTO> booklList = bookBLL.getBookDTOS();

    public void initialize() {
        Button openButton = new Button("Import từ excel");
        openButton.setStyle("-fx-background-color: #2ecc71");
        openButton.setOnAction(e -> openFileChooser());
        bk_root.getChildren().add(openButton);
        //

        AnchorPane.setTopAnchor(openButton, 10.0);
        AnchorPane.setLeftAnchor(openButton, 30.0);
        txt_bk_id.setDisable(true);

     
        tbl_bk.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_bk.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tbl_bk.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tbl_bk.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));

        Collections.reverse(booklList);
        ObservableList<BookDTO> observableMemberList = FXCollections.observableArrayList(booklList);
        tbl_bk.setItems(observableMemberList);


        tbl_bk.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookDTO>() {
            @Override
            public void changed(ObservableValue<? extends BookDTO> observable, BookDTO oldValue, BookDTO newValue) {
                BookDTO selectedItem = tbl_bk.getSelectionModel().getSelectedItem();
                try {
                    BookDTO book = bookBLL.getBookFromId(selectedItem.getId());

                    if (book != null) {
                        txt_bk_id.setText(book.getId());
                        txt_bk_title.setText(book.getTitle());
                        txt_bk_auth.setText(book.getAuthor());
                        txt_bk_st.setText(book.getStatus());
                        txt_bk_id.setDisable(true);
                        btn_add.setText("Update");
                    }

                } catch (NullPointerException n) {
                    return;
                }
            }
        });
    }

    public void btn_new(ActionEvent actionEvent){
        btn_add.setText("Add");
        txt_bk_st.setText("Available");
        txt_bk_st.setDisable(true);
        txt_bk_id.setDisable(false);
        txt_bk_auth.clear();
        txt_bk_title.clear();
        txt_bk_title.requestFocus();
        String newid = bookBLL.getNewId();
        txt_bk_id.setText(newid);

    }
    public void openFileChooser(){
        List<BookDTO> booklist = new ArrayList<>();
        List<BookDTO> errorbooklist = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            try {
                booklist = CommonUtils.ReadExcel(file, BookDTO.class);
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("book_preview_view.fxml"));
                Parent root = fxmlLoader.load();
                BookPreviewController previewController = fxmlLoader.getController();
                previewController.setData(booklist);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("thêm data");
                stage.showAndWait();
                if (previewController.isAccepted()) {
                    for (var book: booklist
                    ) {
                        System.out.println(book.toString());
//                    boolean execres =  new BookBLL().insertBook(book);
//                    if(!execres){
//                        errorbooklist.add(book);
//                        continue;
//                    }
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "sách updated",
                            ButtonType.OK);
                    alert.showAndWait();
                }



                refreshTable();

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void btn_Add(ActionEvent actionEvent){
        txt_bk_id.setDisable(true);
        List<BookDTO> books = bookBLL.getBookDTOS();
        if (txt_bk_id.getText().isEmpty() || txt_bk_title.getText().isEmpty() || txt_bk_auth.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Điền đầy đủ thông tin ",
                    ButtonType.OK);
            
            return;
        }

        if (btn_add.getText().equals("Add")) {

            boolean insertbook = bookBLL.insertBook(new BookDTO(txt_bk_id.getText(),txt_bk_title.getText(),txt_bk_auth.getText(),txt_bk_st.getText()));
            if(insertbook){
                System.out.println("insert book sucess");
            }
            else System.out.println("insert book wrong");
        }
        else {

            if (btn_add.getText().equals("Update")) {

                for (int i = 0; i < books.size(); i++) {
                    if (txt_bk_id.getText().equals(books.get(i).getId())) {

                            BookDTO umeme = new BookDTO(txt_bk_id.getText(),txt_bk_title.getText(),txt_bk_auth.getText(),txt_bk_st.getText());
                            boolean affected = bookBLL.updateBook(umeme);

                            if (affected) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                        "record updated",
                                        ButtonType.OK);
                                Optional<ButtonType> buttonType = alert.showAndWait();
                                
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR,
                                        "update error",
                                        ButtonType.OK);
                                Optional<ButtonType> buttonType = alert.showAndWait();
                            }

                    }
                }
            }
            
        }
        txt_bk_title.clear();
        txt_bk_auth.clear();
        txt_bk_st.clear();
        btn_add.setText("Add");
        txt_bk_id.setDisable(true);
        tbl_bk.getItems().clear();
        refreshTable();
    }

    public void refreshTable(){
        tbl_bk.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_bk.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tbl_bk.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tbl_bk.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));

        booklList = bookBLL.getBookDTOS();
        Collections.reverse(booklList);
        ObservableList<BookDTO> observableMemberList = FXCollections.observableArrayList(booklList);
        tbl_bk.setItems(observableMemberList);


        tbl_bk.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookDTO>() {
            @Override
            public void changed(ObservableValue<? extends BookDTO> observable, BookDTO oldValue, BookDTO newValue) {
                BookDTO selectedItem = tbl_bk.getSelectionModel().getSelectedItem();
                try {
                    BookDTO book = bookBLL.getBookFromId(selectedItem.getId());

                    if (book != null) {
                        txt_bk_id.setText(book.getId());
                        txt_bk_title.setText(book.getTitle());
                        txt_bk_auth.setText(book.getAuthor());
                        txt_bk_st.setText(book.getStatus());
                        txt_bk_id.setDisable(true);
                        btn_add.setText("Update");
                    }

                } catch (NullPointerException n) {
                    return;
                }
            }
        });
    }
    public void btn_dlt(ActionEvent actionEvent){
        if(txt_bk_id.getText().isEmpty() || txt_bk_id.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Điền đầy đủ thông tin ",
                    ButtonType.OK);

            return;
        }
        boolean deleteBook = bookBLL.deleteBook(txt_bk_id.getText());
        if(!deleteBook){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Không thể xóa sách này ",
                    ButtonType.OK);

            return;
        }

    }
    public void img_back(MouseEvent event) throws IOException{
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
