package mkqq.Controller;


import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import mkqq.DTO.MemberDTO;

public class MemberController {
    @FXML
    public TextField mem_id
    , mem_nme
    , mem_addss
    , mem_num;
    @FXML
    public TableView<MemberDTO> mem_tbl;
    @FXML
    public ImageView img_bk;
    @FXML
    public AnchorPane root;
    @FXML
    public Button btn_new;
    @FXML
    public Button btn_add;

    public void initialize() throws ClassNotFoundException {

        mem_id.setDisable(true);
        Class.forName("com.mysql.jdbc.Driver");

        mem_tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        mem_tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        mem_tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        mem_tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));


    }

    public void img_back(MouseEvent event) throws IOException {

    }

    public void btn_new(ActionEvent actionEvent)  {

    }


    public void btn_add(ActionEvent actionEvent)  {

    }

    public void btn_dtl(ActionEvent actionEvent)  {

    }
}
