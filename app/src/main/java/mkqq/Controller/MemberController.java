package mkqq.Controller;


import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import mkqq.MainApp;
import mkqq.BLL.MemberBLL;
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
    public MemberBLL memberBLL = new MemberBLL();
    List<MemberDTO> memberlist = memberBLL.getMemberDTOS();

    public void initialize() throws ClassNotFoundException {

        mem_id.setDisable(true);


        mem_tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        mem_tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        mem_tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        mem_tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));

        Collections.reverse(memberlist);
        ObservableList<MemberDTO> observableMemberList = FXCollections.observableArrayList(memberlist);
        mem_tbl.setItems(observableMemberList);

        mem_tbl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberDTO>() {
            @Override
            public void changed(ObservableValue<? extends MemberDTO> observable, MemberDTO oldValue, MemberDTO newValue) {
                MemberDTO selectedItem = mem_tbl.getSelectionModel().getSelectedItem();
                try {
                    MemberDTO mem = memberBLL.getMemberfromID(selectedItem.getId());

                    if (mem != null) {
                        mem_id.setText(mem.getId());
                        mem_nme.setText(mem.getName());
                        mem_addss.setText(mem.getAddress());
                        mem_num.setText(mem.getContact());
                        mem_id.setDisable(true);
                        btn_add.setText("Update");
                    }

                } catch (NullPointerException n) {
                    return;
                }
            }
        });
    }

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
    }

    public void btn_new(ActionEvent actionEvent)  {
        mem_nme.clear();
        mem_addss.clear();
        mem_num.clear();
        btn_add.setText("Add");
        mem_id.setDisable(false);
        String newid = memberBLL.getNewId();
        mem_id.setText(newid);
    }


    public void btn_add(ActionEvent actionEvent)  {
        mem_id.setDisable(true);
        List<MemberDTO> members = memberBLL.getMemberDTOS();
        if (mem_id.getText().isEmpty() ||
                mem_nme.getText().isEmpty() ||
                mem_addss.getText().isEmpty() ||
                mem_num.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Điền đầy đủ thông tin ",
                    ButtonType.OK);

            return;
        }


        if (btn_add.getText().equals("Add")) {

            boolean insertmember = memberBLL.insertMember(new MemberDTO(mem_id.getText(),mem_nme.getText(),mem_addss.getText(),mem_num.getText()));
            if(insertmember){
                System.out.println("insert sucess");
            }
            else System.out.println("insert wrong");
        }
        else {

            if (btn_add.getText().equals("Update")) {

                for (int i = 0; i < members.size(); i++) {
                    if (mem_id.getText().equals(members.get(i).getId())) {

                            MemberDTO umeme = new MemberDTO(mem_id.getText(),mem_nme.getText(),mem_addss.getText(),mem_num.getText());
                            boolean affected = memberBLL.updateMember(umeme);

                            if (affected) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                        "Record updated!!",
                                        ButtonType.OK);
                                Optional<ButtonType> buttonType = alert.showAndWait();
                                
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR,
                                        "Update error!",
                                        ButtonType.OK);
                                Optional<ButtonType> buttonType = alert.showAndWait();
                            }

                    }
                }
            }
            //ObservableList<MemberDTO> observableMemberList = FXCollections.observableArrayList(members);
            //mem_tbl.setItems(observableMemberList);
        }
        mem_nme.clear();
        mem_addss.clear();
        mem_num.clear();
        btn_add.setText("Add");
        mem_id.setDisable(true);
        mem_tbl.getItems().clear();
        refreshTable();

    }
    public void refreshTable(){
        mem_tbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        mem_tbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        mem_tbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        mem_tbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));

        memberlist = memberBLL.getMemberDTOS();
        Collections.reverse(memberlist);
        ObservableList<MemberDTO> observableMemberList = FXCollections.observableArrayList(memberlist);
        mem_tbl.setItems(observableMemberList);

        mem_tbl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberDTO>() {
            @Override
            public void changed(ObservableValue<? extends MemberDTO> observable, MemberDTO oldValue, MemberDTO newValue) {
                MemberDTO selectedItem = mem_tbl.getSelectionModel().getSelectedItem();
                try {
                    MemberDTO mem = memberBLL.getMemberfromID(selectedItem.getId());

                    if (mem != null) {
                        mem_id.setText(mem.getId());
                        mem_nme.setText(mem.getName());
                        mem_addss.setText(mem.getAddress());
                        mem_num.setText(mem.getContact());
                        mem_id.setDisable(true);
                        btn_add.setText("Update");
                    }

                } catch (NullPointerException n) {
                    return;
                }
            }
        });
    }

    public void btn_dtl(ActionEvent actionEvent)  {
        MemberDTO selectedItem = mem_tbl.getSelectionModel().getSelectedItem();
        if (mem_tbl.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please select a member.",
                    ButtonType.OK);
            Optional<ButtonType> buttonType = alert.showAndWait();
            return;
        } else {

            boolean affected = memberBLL.deleteMember(selectedItem.getId());

            if (affected) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Record deleted!!",
                        ButtonType.OK);
                Optional<ButtonType> buttonType = alert.showAndWait();
            }
        }
        mem_tbl.getItems().clear();
        refreshTable();

    }
    
}
