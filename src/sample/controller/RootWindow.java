package sample.controller;




/*import sample.model.TableOfClients;
import sample.model.Trm_in_classifclientsFоrUpload;
import sample.model.WorkExcelUpload;*/
//import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.UploadClientDiscont;
import sample.model.*;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;



public class RootWindow implements Initializable{


    @FXML private TextArea logError;
    @FXML private ProgressBar progressBar;
    //FXML private TableColumn colTeleport;
    @FXML private TableView<TableWithClients> tableClients;
    @FXML private TableColumn<TableWithClients, Boolean> colUpload;
    @FXML private TableColumn<TableWithClients, String> colClient;
    @FXML private TableColumn<TableWithClients, String> colCard;
    @FXML private TableColumn<TableWithClients, Boolean> colCardExist;
    @FXML private TableColumn<TableWithClients, String> colPhone;
    @FXML private TableColumn<TableWithClients,Boolean> colPhoneExist;
    @FXML private TableColumn<TableWithClients,Boolean> colTeleport;
    @FXML private TableColumn<TableWithClients, String> colDateBirthday;
    @FXML private TableColumn<TableWithClients,String> colComment;
    @FXML private TableColumn<TableWithClients,String> colOldGroupId;
    @FXML private TableColumn<TableWithClients,String> colOldGroupName;
    ObservableList<TableWithClients> tableWithClients = FXCollections.observableArrayList();

    @FXML
    ObservableList<Trm_in_classifclientsFоrUpload> observableArrayList = FXCollections.observableArrayList(UploadClientDiscont.listClassif);

    @FXML
    ComboBox<Trm_in_classifclientsFоrUpload> classifClientsBox= new ComboBox<Trm_in_classifclientsFоrUpload>();
    @FXML
    ComboBox<TypeCards> typeCarfsBox = new ComboBox<>();
    @FXML
    TextField pathFile;

    @FXML
    ObservableList<TypeCards> typeCardsObservableList = creatListTypeCard();



    FileChooser fileChooser = new FileChooser();
    File file;


    public void initialize(URL location, ResourceBundle resources) {



        classifClientsBox.setItems(observableArrayList);

        typeCarfsBox.setItems(typeCardsObservableList);

        //colComment.
        colUpload.setCellFactory(CheckBoxTableCell.forTableColumn(colUpload));
        colCardExist.setCellFactory(CheckBoxTableCell.forTableColumn(colCardExist));
        colTeleport.setCellFactory(CheckBoxTableCell.forTableColumn(colTeleport));
        colPhoneExist.setCellFactory(CheckBoxTableCell.forTableColumn(colPhoneExist));
        colTeleport.setCellValueFactory(new PropertyValueFactory<TableWithClients,Boolean>("teleport"));
        colUpload.setCellValueFactory(new PropertyValueFactory<TableWithClients,Boolean>("upload"));
        colClient.setCellValueFactory(new PropertyValueFactory<TableWithClients,String>("client"));
        colCard.setCellValueFactory(new PropertyValueFactory<TableWithClients,String>("card"));
        colCardExist.setCellValueFactory(new PropertyValueFactory<TableWithClients,Boolean>("cardExist"));
        colPhone.setCellValueFactory(new PropertyValueFactory<TableWithClients,String>("phone"));
        colOldGroupId.setCellValueFactory(new PropertyValueFactory<TableWithClients,String>("oldGroupId"));
        colOldGroupName.setCellValueFactory(new PropertyValueFactory<TableWithClients,String>("oldGroupName"));
        colPhoneExist.setCellValueFactory(new PropertyValueFactory<TableWithClients,Boolean>("phoneExist"));
        colDateBirthday.setCellValueFactory(new PropertyValueFactory<TableWithClients,String>("dateBirthday"));
        colComment.setCellValueFactory(new PropertyValueFactory<TableWithClients,String>("comment"));
        try{
            pathFile.setText("D:\\OneDrive\\java\\Demo\\zagruzka.xlsx");
            file = new File("D:\\OneDrive\\java\\Demo\\zagruzka.xlsx");
        }
        catch (Exception e) {
        }




    }

    private ObservableList<TypeCards> creatListTypeCard() {
        ObservableList<TypeCards> result ;
        List<TypeCards> typeCardsList = new ArrayList<>();
        typeCardsList.add(new TypeCards(99990,13,"Vip Брусничка"));
        typeCardsList.add(new TypeCards(999703,13,"Брусничка"));
        typeCardsList.add(new TypeCards(99999,14,"Рыжая"));
        result = FXCollections.observableArrayList(typeCardsList);
        return result;
    }


    @FXML
    public void onСlickSelectBtn(ActionEvent actionEvent) {
        Window window = ((Node) (actionEvent.getSource())).getScene().getWindow();
        file = fileChooser.showOpenDialog(window);
        if (file != null) {
            pathFile.setText(file.getAbsolutePath());
        }
        actionEvent.consume();
    }

    @FXML
    public void onClickUploadBtn(ActionEvent actionEvent) {
        String idClassifClient = "";

        tableWithClients.clear();
        try {
            idClassifClient = classifClientsBox.getValue().id;
        }
        catch (Exception e)
        {
            //System.out.println("не выбрана группа классификатора");
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("Внимание");
            dialog.setHeaderText("не выбрана группа классификатора!");
            dialog.showAndWait();
            return;
        }



        if ((file== null)||!file.exists()) {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("Внимание");
            dialog.setHeaderText("не выбран файл или выбранный файл более не существует!");
            dialog.showAndWait();
          //  System.out.println("не выбран файл");
            return;
        }

        ArrayList<TableOfClients> tableOfClients  = new ArrayList<TableOfClients>();
        tableOfClients =  WorkExcelUpload.getAll(file,typeCarfsBox.getValue().getPrefix().toString(),typeCarfsBox.getValue().getLen());


        ProgressCreatTable task = new ProgressCreatTable(idClassifClient,tableOfClients);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(task.getValue().size());
        //System.out.println(task.getTableWithClients());
        tableWithClients = task.getTableWithClients();
        tableClients.setItems(tableWithClients);
        tableClients.setEditable(true);
        tableClients.getColumns();
        logError.setText(UploadClientDiscont.logEr);



        Alert dialogUpload = new Alert(Alert.AlertType.INFORMATION);
        dialogUpload.initStyle(StageStyle.UTILITY);
        dialogUpload.setTitle("Внимание");
        dialogUpload.setHeaderText("Клиенты добавлены в таблицу!");
        dialogUpload.showAndWait();


    }


    public void onClickCreatBtn(ActionEvent actionEvent) {
      //  String comment = "";
       /* System.out.println(classifClientsBox.getValue().id);
        System.out.println(classifClientsBox.getValue().name);*/
        for (TableWithClients clients:
                tableWithClients) {
            if (!clients.isCardExist()&&!clients.isPhoneExist()){
         //       comment = "Создадим новго клиента" + "\n";
                String classifid =  classifClientsBox.getValue().id;
                String uiid = WorkWithUiid.getNewUiid(UploadClientDiscont.stmt);
          //      System.out.println(uiid);
                //создадим клиента
                Boolean createclient = Trm_in_clients_upload.creatClient(clients.getClient(),classifid,uiid,clients.getDateBirthday(),UploadClientDiscont.stmt);
          //      System.out.println(createclient);
                if (!createclient) {return;}
                String maxIdCardStr=Trm_in_cards_upload.getMaxId(UploadClientDiscont.stmt);
           //     System.out.println(maxIdCardStr);
                int maxIdCard =0;
                try {
                    maxIdCard = Integer.parseInt(maxIdCardStr);
                }
                catch (Exception e)
                {

                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date currentDate = new Date();
                String curdate = sdf.format(currentDate);
                //создадим карты по телефону и карте клиента
                boolean creatcard=true;
                boolean comunicationCardAndClient = true;
                if (clients.getCard().trim().length()>0) {
                    creatcard = Trm_in_cards_upload.creatCard(clients.getCard(), curdate, Integer.toString(maxIdCard + 1), UploadClientDiscont.stmt);
                    comunicationCardAndClient = TrmInCardClient.creatCardClient(Integer.toString(maxIdCard+1),uiid,UploadClientDiscont.stmt);
                }
                if (clients.getPhone().trim().length()>0) {
                    boolean creatphone = Trm_in_cards_upload.creatCard(clients.getPhone(), curdate, Integer.toString(maxIdCard + 2), UploadClientDiscont.stmt);
                    //свяжем карты с клиентом
                    boolean comunicationPhoneAndClient = TrmInCardClient.creatCardClient(Integer.toString(maxIdCard + 2), uiid, UploadClientDiscont.stmt);
                }
                clients.setCardExist(true);
                clients.setPhoneExist(true);
                /*clients.setOldGroupId(classifid);
                clients.setOldGroupName(classifClientsBox.getValue().name);
                clients.setTeleport(false);*/

              //  return;
                //String sql_create_clients = "replace into trm_in_clients (sur_name,classifclient,allow_paycash,active,id) values ('"+$cell1."','."'28','1','1',".'"'. $uuid.'")'; //запрос создания клиента
            }else if (!clients.isCardExist()&&clients.isPhoneExist()&&(clients.getCard().trim().length()>0)){
       //         comment = comment+ "Создадим новую карту существующего клиента по карте" + "\n";
                //находим клиента
                String idCards = Trm_in_cards_upload.IdByStart_card_code(UploadClientDiscont.stmt,clients.getPhone());
                String idClient = TrmInCardClient.getClientByCard(UploadClientDiscont.stmt,idCards);
                //создаем новую карту
                String maxIdCardStr=Trm_in_cards_upload.getMaxId(UploadClientDiscont.stmt);
           //     System.out.println(maxIdCardStr);
                int maxIdCard =0;
                try {
                    maxIdCard = Integer.parseInt(maxIdCardStr);
                }
                catch (Exception e)
                {

                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date currentDate = new Date();
                String curdate = sdf.format(currentDate);
                boolean creatcard = Trm_in_cards_upload.creatCard(clients.getCard(),curdate,Integer.toString(maxIdCard+1),UploadClientDiscont.stmt);
                //свяжем карты с клиентом
                boolean comunicationCardAndClient = TrmInCardClient.creatCardClient(Integer.toString(maxIdCard+1),idClient,UploadClientDiscont.stmt);
                clients.setCardExist(true);
                clients.setPhoneExist(true);
              /*  clients.setOldGroupId(classifid);
                clients.setOldGroupName(classifClientsBox.getValue().name);
                clients.setTeleport(false);*/
            }else if (clients.isCardExist()&&!clients.isPhoneExist()&&(clients.getPhone().trim().length()>0)){
           //     comment = comment +"Создадим новую карту существующего клиента по телефону" + "\n";
                //находим клиента
                String idCards = Trm_in_cards_upload.IdByStart_card_code(UploadClientDiscont.stmt,clients.getCard());
                String idClient = TrmInCardClient.getClientByCard(UploadClientDiscont.stmt,idCards);
                //создаем новую карту
                String maxIdCardStr=Trm_in_cards_upload.getMaxId(UploadClientDiscont.stmt);
           //     System.out.println(maxIdCardStr);
                int maxIdCard =0;
                try {
                    maxIdCard = Integer.parseInt(maxIdCardStr);
                }
                catch (Exception e)
                {

                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date currentDate = new Date();
                String curdate = sdf.format(currentDate);
                boolean creatphone = Trm_in_cards_upload.creatCard(clients.getPhone(),curdate,Integer.toString(maxIdCard+2),UploadClientDiscont.stmt);
                boolean comunicationPhoneAndClient = TrmInCardClient.creatCardClient(Integer.toString(maxIdCard+2),idClient,UploadClientDiscont.stmt);
                clients.setCardExist(true);
                clients.setPhoneExist(true);

            }
            if (clients.isTeleport()){
           //     comment = comment + "Перенесем клиента в новую группу" + "\n";
                //найдем клиента
                String classifid ="";
                if (clients.getCard().trim().length()>0) {
                    System.out.println(clients.getCard().trim().length());
                    System.out.println(clients.getCard());
                    String idCards = Trm_in_cards_upload.IdByStart_card_code(UploadClientDiscont.stmt, clients.getCard());
                    String idClient = TrmInCardClient.getClientByCard(UploadClientDiscont.stmt, idCards);
                    //переместим клиента в другую группу
                    classifid = classifClientsBox.getValue().id;
                    Trm_in_clients_upload.changeGroup(idClient, classifid, UploadClientDiscont.stmt);
                }
                String idPhone = Trm_in_cards_upload.IdByStart_card_code(UploadClientDiscont.stmt,clients.getPhone());
                String idClientPhone = TrmInCardClient.getClientByCard(UploadClientDiscont.stmt,idPhone);
                //переместим клиента в другую группу
                classifid =  classifClientsBox.getValue().id;
                Trm_in_clients_upload.changeGroup(idClientPhone,classifid,UploadClientDiscont.stmt);
                clients.setOldGroupId(classifid);
                clients.setOldGroupName(classifClientsBox.getValue().name);
                clients.setTeleport(false);
            }
            tableClients.refresh();
       //     System.out.println(comment);

        }
        //tableClients.getColumns();
        Alert dialogCreat = new Alert(Alert.AlertType.INFORMATION);
        dialogCreat.initStyle(StageStyle.UTILITY);
        dialogCreat.setTitle("Внимание");
        dialogCreat.setHeaderText("Клиенты загружены!");
        dialogCreat.showAndWait();

    }

    public void selectClassifClientsBox(ActionEvent actionEvent) {
        boolean teleport = false;
        for (TableWithClients clients:
                tableWithClients) {
            if (!clients.getOldGroupId().equals(classifClientsBox.getValue().id)){teleport = true;}else {teleport = false;}
            clients.setTeleport(teleport);
            tableClients.refresh();
       }
    }

    public void selectTypeCarfsBox(ActionEvent actionEvent) {
    }
}
